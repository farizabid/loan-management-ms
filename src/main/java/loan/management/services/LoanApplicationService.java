package loan.management.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import loan.management.constants.GeneralConstant;
import loan.management.dto.*;
import loan.management.models.LoanApplication;
import loan.management.models.LoanInstallment;
import loan.management.models.type.LoanApplicationStatus;
import loan.management.repositories.LoanApplicationRepository;
import loan.management.repositories.LoanInstallmentRepository;
import loan.management.utils.ObjectActiveAndCreatedDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@ApplicationScoped
public class LoanApplicationService {

    @Inject
    LoanApplicationRepository loanApplicationRepository;

    @Inject
    LoanInstallmentRepository loanInstallmentRepository;

    @Inject
    ApplicationCounterService applicationCounterService;

    private static final Logger log = LoggerFactory.getLogger(LoanApplicationService.class);

    @Transactional
    public BaseResponse<Object> createLoanApplication(LoanApplicationRequestDto loanApplicationRequestDto) throws Exception {
        try {
            LoanApplication customerExisting = loanApplicationRepository.findByCustomerId(loanApplicationRequestDto.customerId);
            if (customerExisting != null){
                return new BaseResponse<>(GeneralConstant.FAILED_CODE, "customerId is already registered", "");
            }
            LoanApplication loanApplication = new LoanApplication();
            loanApplication.referenceNo = applicationCounterService.generateApplicationNo();
            loanApplication.customerName = loanApplicationRequestDto.customerName;
            loanApplication.customerId = loanApplicationRequestDto.customerId;
            loanApplication.loanPurpose = loanApplicationRequestDto.loanPurpose;
            loanApplication.loanAmount = loanApplicationRequestDto.loanAmount;
            loanApplication.interestRate = loanApplicationRequestDto.interestRate;
            loanApplication.tenor = loanApplicationRequestDto.tenor;
            loanApplication.status = LoanApplicationStatus.DRAFT;
            ObjectActiveAndCreatedDateUtil.registerObject(loanApplication);
            loanApplication.persist();

            return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, loanApplication);
        } catch (Exception ex){
            log.info("Error in createLoanApplication");
            throw new Exception(ex);
        }
    }

    public BaseResponse<Object> calculate(LoanCalculatorRequestDto request) throws Exception {

        try {
            String loanApplicationId = request.loanApplicationId;
            LoanApplication loanApplication = LoanApplication.findById(loanApplicationId);

            if (loanApplication == null){
                return new BaseResponse<>(GeneralConstant.FAILED_CODE, "application not found", "");
            }

            BigDecimal loanAmount = BigDecimal.valueOf(request.loanAmount);
            BigDecimal annualRate = BigDecimal.valueOf(request.interestRate);
            Integer tenor = request.tenor;

            // monthlyRate
            BigDecimal monthlyRate = annualRate
                    .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

            // (1 + r)^n
            BigDecimal onePlusRPowerN =
                    BigDecimal.ONE.add(monthlyRate)
                            .pow(tenor);

            // numerator
            BigDecimal numerator =
                    monthlyRate.multiply(onePlusRPowerN);

            // denominator
            BigDecimal denominator =
                    onePlusRPowerN.subtract(BigDecimal.ONE);

            // installment
            BigDecimal installment =
                    loanAmount.multiply(
                            numerator.divide(
                                    denominator,
                                    10,
                                    RoundingMode.HALF_UP
                            )
                    );

            installment = roundUpThousand(installment);

            // total payment
            BigDecimal principalAmount = roundUpThousand(installment.multiply(BigDecimal.valueOf(tenor)));

            // total interest
            BigDecimal totalInterest = roundUpThousand(principalAmount.subtract(loanAmount));

            LoanCalculatorResponseDto response = new LoanCalculatorResponseDto();
            response.loanApplicationId = request.loanApplicationId;
            response.referenceNo = request.referenceNo;
            response.loanAmount = request.loanAmount;
            response.interestRate = request.interestRate;
            response.tenor = request.tenor;
            response.loanPurpose = request.loanPurpose;
            response.installment = installment;
            response.principalAmount = principalAmount;
            response.interestAmount = totalInterest;

            return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, response);
        } catch (Exception ex){
            log.info("Error in calculate : ", ex);
            throw new Exception(ex);
        }
    }

    @Transactional
    public BaseResponse<Object> submitCalculation(LoanInstallmentRequestDto requestDto) throws Exception {
        try{
            LoanInstallment loanInstallment = loanInstallmentRepository.findByApplicationId(requestDto.loanApplicationId);

            if (loanInstallment == null){
                loanInstallment = new LoanInstallment();
                ObjectActiveAndCreatedDateUtil.registerObject(loanInstallment);
            } else {
                ObjectActiveAndCreatedDateUtil.updateObject(loanInstallment, true);
            }

            loanInstallment.loanApplicationId = requestDto.loanApplicationId;
            loanInstallment.interestAmount = requestDto.interestAmount;
            loanInstallment.loanAmount = requestDto.loanAmount;
            loanInstallment.installmentAmount = requestDto.installment;
            loanInstallment.interestRate = requestDto.interestRate;
            loanInstallment.principalAmount = requestDto.principalAmount;
            loanInstallment.tenor = requestDto.tenor;

            loanInstallment.persist();

            // update status application
            updateStatusApplication(loanInstallment.loanApplicationId, LoanApplicationStatus.SUBMITTED);

            return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, loanInstallment);

        } catch (Exception ex){
            log.info("Error in submitCalculation : ", ex);
            throw new Exception(ex);
        }
    }

    public void updateStatusApplication(String loanId, LoanApplicationStatus status){
        LoanApplication loanApplication = LoanApplication.findById(loanId);
        loanApplication.status = status;
        ObjectActiveAndCreatedDateUtil.updateObject(loanApplication, true);

        loanApplication.persist();
    }

    public BaseResponse<Object> getLoanApplicationDetail(LoanApplicationRequestDto loanApplicationRequestDto) throws Exception{
        try {
            if (loanApplicationRequestDto.loanApplicationId == null) {
                return new BaseResponse<>(GeneralConstant.FAILED_CODE, "loanApplicationId can't be empty", "");
            }

            LoanApplication loanApplication = LoanApplication.findById(loanApplicationRequestDto.loanApplicationId);

            return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, loanApplication);
        } catch (Exception ex){
            log.info("Error in getLoanApplicationDetail :",ex);
            throw new Exception(ex);
        }
    }

    private BigDecimal roundUpThousand(BigDecimal value) {

        return value
                .divide(BigDecimal.valueOf(1000), 0, RoundingMode.UP)
                .multiply(BigDecimal.valueOf(1000));
    }

    public BaseResponse<Object> getListLoanApplication(LoanApplicationFilterDto loanApplicationRequestDto) throws Exception{
        try {
            List<LoanApplication> loanApplications = loanApplicationRepository.findByFilter(loanApplicationRequestDto);
            return new BaseResponse<Object>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, loanApplications);
        } catch (Exception ex){
            log.info("Error in getListLoanApplication : ", ex);
            throw new Exception(ex);
        }
    }

}
