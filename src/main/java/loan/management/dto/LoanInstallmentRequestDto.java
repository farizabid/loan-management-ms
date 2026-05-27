package loan.management.dto;

import java.math.BigDecimal;

public class LoanInstallmentRequestDto {
    public String loanApplicationId;
    public String referenceNo;
    public Double loanAmount;
    public Double interestRate;
    public Integer tenor;
    public String loanPurpose;
    public Double installment;
    public Double principalAmount;
    public Double interestAmount;
}
