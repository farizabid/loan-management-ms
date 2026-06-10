package loan.management.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class CreateTicketPayloadDTO {
        public String referenceId;
        public String referenceNumber;
        public String customerName;
        public String customerId;
        public JsonNode data;
}