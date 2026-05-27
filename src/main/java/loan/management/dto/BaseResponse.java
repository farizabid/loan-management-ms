package loan.management.dto;

public class BaseResponse<T> {
    public Long status;
    public String message;
    public T payload;

    public BaseResponse() {
    }

    public BaseResponse(
            Long status,
            String message,
            T data
    ) {

        this.status = status;
        this.message = message;
        this.payload = data;
    }
}
