package loan.management.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import loan.management.models.LoanInstallment;

@ApplicationScoped
public class LoanInstallmentRepository implements PanacheRepository<LoanInstallment> {

    public LoanInstallment findByApplicationId(String applicationId){
        return find(
                "loanApplicationId",
                applicationId
        ).firstResult();
    }
}
