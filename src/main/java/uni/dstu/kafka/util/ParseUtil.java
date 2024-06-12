package uni.dstu.kafka.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import uni.dstu.kafka.dto.JsonDto;
import uni.dstu.kafka.exception.InvalidHttpHeadersException;

@UtilityClass
public class ParseUtil {

    public static HttpHeaders getHttpHeaders(JsonDto jsonDto) {
        HttpHeaders headers = new HttpHeaders();
        if (jsonDto.getHttpHeaders() != null) {
            for (String httpHeader : jsonDto.getHttpHeaders()) {
                if (httpHeader.contains(":")) {
                    String[] headerParts = httpHeader.split(":");
                    headers.add(headerParts[0].trim(), headerParts[1].trim());
                } else {
                    throw new InvalidHttpHeadersException();
                }
            }
        }
        return headers;
    }
}
