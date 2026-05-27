package loan.management.dto;

import java.math.BigDecimal;

public class LoanCalculatorResponseDto {
    public String loanApplicationId;
    public String referenceNo;
    public Double loanAmount;
    public Double interestRate;
    public Integer tenor;
    public String loanPurpose;
    public BigDecimal installment;
    public BigDecimal principalAmount;
    public BigDecimal interestAmount;
}
