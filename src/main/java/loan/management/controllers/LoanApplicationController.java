package loan.management.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import loan.management.constants.GeneralConstant;
import loan.management.dto.*;
import loan.management.models.LoanApplication;
import loan.management.services.LoanApplicationService;
import loan.management.services.UserService;

@Path("/api/v1/loan-application")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoanApplicationController {

    @Inject
    LoanApplicationService loanApplicationService;

    @POST
    @RolesAllowed({"ADMIN","USER"})
    @Path("/create")
    public BaseResponse<Object> create(
            LoanApplicationRequestDto requestDto
    ) throws Exception {
        return loanApplicationService.createLoanApplication(requestDto);
    }

    @POST
    @RolesAllowed({"ADMIN","USER"})
    @Path("/calculate")
    public BaseResponse<Object> calculate(
            LoanCalculatorRequestDto requestDto
    ) throws Exception {
        return loanApplicationService.calculate(requestDto);
    }

    @POST
    @RolesAllowed({"ADMIN","USER"})
    @Path("/submitCalculation")
    public BaseResponse<Object> submitCalculation(
            LoanInstallmentRequestDto requestDto
    ) throws Exception {
        return loanApplicationService.submitCalculation(requestDto);
    }

    @POST
    @RolesAllowed({"ADMIN","USER"})
    @Path("/getLoanApplicationDetail")
    public BaseResponse<Object> getLoanApplicationDetail(
            LoanApplicationRequestDto requestDto
    ) throws Exception {
        return loanApplicationService.getLoanApplicationDetail(requestDto);
    }

    @POST
    @RolesAllowed({"ADMIN","USER"})
    @Path("/getListLoanApplication")
    public BaseResponse<Object> getListLoanApplication(
            LoanApplicationFilterDto requestDto
    ) throws Exception {
        return loanApplicationService.getListLoanApplication(requestDto);
    }

    @GET
    @RolesAllowed({"ADMIN","USER"})
    @Path("/getSummaryDashboard")
    public BaseResponse<Object> getSummaryDashboard()
            throws Exception {
        return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, loanApplicationService.getSummaryDashboard());
    }

    @POST
    @RolesAllowed({"ADMIN","USER"})
    @Path("/createTicket")
    public BaseResponse<Object> createTicket(
            LoanApplicationRequestDto requestDto
    ) throws Exception {
        return loanApplicationService.createTicket(requestDto);
    }

    @POST
    @RolesAllowed({"ADMIN","USER"})
    @Path("/cancelTicket")
    public BaseResponse<Object> cancelTicket(
            CancelTicketPayloadDTO requestDto
    ) throws Exception {
        return loanApplicationService.cancelTicket(requestDto);
    }
}