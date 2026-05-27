package loan.management.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import loan.management.dto.*;
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
}