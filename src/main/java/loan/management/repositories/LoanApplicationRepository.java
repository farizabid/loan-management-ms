package loan.management.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import loan.management.dto.LoanApplicationFilterDto;
import loan.management.models.LoanApplication;
import loan.management.models.type.LoanApplicationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class LoanApplicationRepository implements PanacheRepository<LoanApplication> {

    public LoanApplication findByCustomerId(String customerId){
        return find(
                "customerId",
                customerId
        ).firstResult();
    }

    public List<LoanApplication> findByFilter(LoanApplicationFilterDto filter) {
        StringBuilder query = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (filter.customerName != null && !filter.customerName.isBlank()) {
            query.append(" and lower(customerName) like :customerName");
            params.put("customerName", "%" + filter.customerName.toLowerCase() + "%");
        }

        if (filter.referenceNo != null && !filter.referenceNo.isBlank()) {
            query.append(" and referenceNo = :referenceNo");
            params.put("referenceNo", filter.referenceNo);
        }

        if (filter.customerId != null && !filter.customerId.isBlank()) {
            query.append(" and customerId = :customerId");
            params.put("customerId", filter.customerId);
        }

        if (filter.status != null && !filter.status.isBlank()) {
            query.append(" and status = :status");
            params.put("status", filter.status);
        }

        int page = filter.page != null ? filter.page : 0;
        int size = filter.size != null ? filter.size : 10;

        return find(query.toString(), params)
                .page(page, size)
                .list();
    }

    public long countLoanByPeriod(LocalDate start, LocalDate end) {

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        return count(
                "createdDatetime between ?1 and ?2",
                startDateTime, endDateTime
        );
    }

    public long countLoanByStatusAndPeriod(LoanApplicationStatus status, LocalDate start, LocalDate end) {

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        return count(
                "status = ?1 and createdDatetime between ?2 and ?3",
                status,
                startDateTime,
                endDateTime
        );
    }

    public List<LoanApplication> findRecentActivity() {
        return find("order by createdDatetime desc")
                .page(0, 9)
                .list();
    }

    public List<LoanApplication> findApprovedWithDate(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        return find(
                "status = ?1 and createdDatetime between ?2 and ?3",
                LoanApplicationStatus.APPROVED, startDateTime, endDateTime
        ).list();
    }

}
