package loan.management.dto;

import java.math.BigDecimal;

public class LoanCalculatorRequestDto {
    public String loanApplicationId;
    public String referenceNo;
    public Double loanAmount;
    public Double interestRate;
    public Integer tenor;
    public String loanPurpose;
}
