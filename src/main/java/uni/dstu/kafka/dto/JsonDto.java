package uni.dstu.kafka.dto;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class JsonDto {
    @Nonnull
    private String requestType;
    @Nonnull
    private String url;
    private String body;
    private String[] httpHeaders;
    private String pathAndQuery;
}
