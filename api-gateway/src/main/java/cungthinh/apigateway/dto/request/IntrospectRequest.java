package cungthinh.apigateway.dto.request;

import lombok.Data;

@Data
public class IntrospectRequest {
    String token;

    public IntrospectRequest(String token) {
        this.token = token;
    }
}
