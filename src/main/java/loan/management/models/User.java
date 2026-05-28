package loan.management.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import loan.management.models.type.LoanApplicationStatus;
import loan.management.models.type.UserType;
import loan.management.utils.CommonObjectActiveAndCreatedDate;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User extends CommonObjectActiveAndCreatedDate implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column(name = "user_id", length = 36, nullable = false)
    @NotNull(message = "Please provide a user_id")
    public String userId;

    @Column(name = "user_name", length = 256, nullable = false)
    public String username;

    @Column(name = "password", length = 256, nullable = false)
    public String password;

    @Column(name = "email", length = 256, nullable = true)
    public String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = true, columnDefinition = "varchar(255) default 'USER'")
    public UserType role = UserType.USER;
}
