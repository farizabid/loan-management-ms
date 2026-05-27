package loan.management.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import loan.management.models.ApplicationCounter;
import loan.management.repositories.ApplicationCounterRepository;
import loan.management.utils.ObjectActiveAndCreatedDateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class ApplicationCounterService {

    @Inject
    ApplicationCounterRepository applicationCounterRepository;

    public static String REFERENCE_NUMBER_TYPE = "REFERENCE-NUMBER";

    @Transactional
    public synchronized String generateApplicationNo() {

        String prefix = "APP";
        String period = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

        ApplicationCounter counter =
                applicationCounterRepository.findByTypeAndPeriod(REFERENCE_NUMBER_TYPE, period);

        if (counter == null) {

            counter = new ApplicationCounter();
            counter.type = REFERENCE_NUMBER_TYPE;
            counter.period = period;
            counter.value = 1;
            ObjectActiveAndCreatedDateUtil.registerObject(counter);
            counter.persist();

        } else {

            counter.value++;
        }

        String runningNo =
                String.format("%03d", counter.value);

        return prefix+ period + runningNo;
    }
}