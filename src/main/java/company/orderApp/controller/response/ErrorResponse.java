package company.orderApp.controller.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    public ErrorResponse(RuntimeException e) {
        this.message = e.getMessage();
    }
}
