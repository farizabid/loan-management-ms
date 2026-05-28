package loan.management.dto;

import loan.management.models.type.LoanApplicationStatus;

public class LoanApplicationFilterDto {

    public String customerId;
    public String customerName;
    public String referenceNo;
    public LoanApplicationStatus status;
    public Integer page;
    public Integer size;
}