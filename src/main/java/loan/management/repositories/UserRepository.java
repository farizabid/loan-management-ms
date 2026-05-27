package loan.management.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import loan.management.models.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(
            String username
    ) {

        return find(
                "username",
                username
        ).firstResult();
    }
}