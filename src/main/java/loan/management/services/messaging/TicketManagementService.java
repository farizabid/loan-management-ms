package loan.management.services.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import loan.management.dto.CancelTicketPayloadDTO;
import loan.management.dto.CreateTicketPayloadDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TicketManagementService {

    private static final Logger log = LoggerFactory.getLogger(TicketManagementService.class);

    @Inject
    @Channel("inform-ticket-out")
    Emitter<String> emitterInformTicket;

    private final String KEY_REGISTER_TICKET = "register-ticket";
    private final String KEY_CANCEL_TICKET = "cancel-ticket";

    public void informTicketRegister(CreateTicketPayloadDTO msgNodePayload) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        log.info("informTicketRegister : {}", om.writeValueAsString(msgNodePayload));

        RecordHeaders rh = new RecordHeaders();
        rh.add("type", KEY_REGISTER_TICKET.getBytes());

        OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String>builder()
                .withKey(KEY_REGISTER_TICKET)
                .withHeaders(rh)
                .build();

        log.debug("informTicketRegister - metadata key={}", metadata.getKey());

        emitterInformTicket.send(Message.of(om.writeValueAsString(msgNodePayload)).addMetadata(metadata));
    }

    public void informTicketCancel(CancelTicketPayloadDTO msgNodePayload) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        log.info("informTicketCancel : {}", om.writeValueAsString(msgNodePayload));

        RecordHeaders rh = new RecordHeaders();
        rh.add("type", KEY_CANCEL_TICKET.getBytes());

        OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String>builder()
                .withKey(KEY_CANCEL_TICKET)
                .withHeaders(rh)
                .build();

        log.debug("informTicketCancel - metadata key={}", metadata.getKey());

        emitterInformTicket.send(Message.of(om.writeValueAsString(msgNodePayload)).addMetadata(metadata));
    }
}