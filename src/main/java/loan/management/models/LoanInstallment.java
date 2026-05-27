package loan.management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import loan.management.utils.CommonObjectActiveAndCreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_installment")
public class LoanInstallment extends CommonObjectActiveAndCreatedDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "loan_installment_id", length = 36, nullable = false)
    @NotNull(message = "Please provide a loan_installment_id")
    public String loanInstallmentId;

    @Column(name = "loan_application_id", length = 36, nullable = false)
    public String loanApplicationId;

    @Column(name = "loan_amount", length = 50, nullable = false)
    public Double loanAmount;

    @Column(name = "principal_amount", length = 50, nullable = false)
    public Double principalAmount;

    @Column(name = "interest_rate", length = 50, nullable = false)
    public Double interestRate;

    @Column(name = "interest_amount", length = 50, nullable = false)
    public Double interestAmount;

    @Column(name = "tenor", length = 50, nullable = false)
    public Integer tenor;

    @Column(name = "installment_amount", length = 50, nullable = false)
    public Double installmentAmount;
}
