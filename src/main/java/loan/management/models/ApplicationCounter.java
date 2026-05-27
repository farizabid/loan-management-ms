package loan.management.models;

import jakarta.persistence.*;
import loan.management.utils.CommonObjectActiveAndCreatedDate;

import java.io.Serializable;

@Entity
@Table(name = "application_counter")
public class ApplicationCounter extends CommonObjectActiveAndCreatedDate implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column(name = "application_counter_id", length = 36, nullable = false)
    public String applicationCounterId;

    @Column(name = "period", length = 50, nullable = false)
    public String period;

    @Column(name = "type", length = 50, nullable = false)
    public String type;

    @Column(name = "value", length = 50, nullable = false)
    public Integer value;

}