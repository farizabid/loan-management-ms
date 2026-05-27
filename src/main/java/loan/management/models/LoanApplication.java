package loan.management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import loan.management.models.type.LoanApplicationStatus;
import loan.management.utils.CommonObjectActiveAndCreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_application")
public class LoanApplication extends CommonObjectActiveAndCreatedDate implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column(name = "loan_application_id", length = 36, nullable = false)
    @NotNull(message = "Please provide a loan_application_id")
    public String loanApplicationId;

    @Column(name = "reference_no", length = 56, nullable = false)
    public String referenceNo;

    @Column(name = "customer_name", length = 256, nullable = false)
    public String customerName;

    @Column(name = "customer_id", length = 256, nullable = false)
    public String customerId;

    @Column(name = "loan_amount", length = 50, nullable = false)
    public Double loanAmount;

    @Column(name = "interest_rate", length = 50, nullable = false)
    public Double interestRate;

    @Column(name = "tenor", length = 50, nullable = false)
    public Integer tenor;

    @Column(name = "loan_purpose", length = 256, nullable = false)
    public String loanPurpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true, columnDefinition = "varchar(255) default 'NONE'")
    public LoanApplicationStatus status = LoanApplicationStatus.NONE;

    @Column(name = "approved_date", nullable = true)
    public LocalDateTime approvedDate;


}
