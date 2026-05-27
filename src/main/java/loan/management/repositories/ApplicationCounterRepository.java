package loan.management.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import loan.management.models.ApplicationCounter;

@ApplicationScoped
public class ApplicationCounterRepository implements PanacheRepository<ApplicationCounter> {

    public ApplicationCounter findByTypeAndPeriod(
            String type, String period
    ) {

        return find(
                "type = ?1 and period = ?2",
                type, period
        ).firstResult();
    }

}
