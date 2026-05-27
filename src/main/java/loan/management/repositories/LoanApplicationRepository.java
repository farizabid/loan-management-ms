package loan.management.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import loan.management.models.LoanApplication;

@ApplicationScoped
public class LoanApplicationRepository implements PanacheRepository<LoanApplication> {

    public LoanApplication findByCustomerId(String customerId){
        return find(
                "customerId",
                customerId
        ).firstResult();
    }
}
