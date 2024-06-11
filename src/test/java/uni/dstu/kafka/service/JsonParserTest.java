package uni.dstu.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JsonParserTest {

    @Autowired
    private JsonParser parser;

    @Test
    void testJsonParsingMandatoryFields() {
        assertThrows(
                JsonMappingException.class,
                () -> parser.parse("{}")
        );

        assertThrows(
                JsonMappingException.class,
                () -> parser.parse("""
                        {"requestType": %s}
                        """.formatted(quote("GET")))
        );

        assertDoesNotThrow(
                () -> parser.parse("""
                        {
                          "requestType": %s,
                          "url": %s
                        }
                        """.formatted(quote("GET"), quote("https://localhost:8080")))
        );
    }

    @Test
    void testJsonParsing() throws JsonProcessingException {
        final String JSON = """
                {
                  "requestType": %s,
                  "url": %s,
                  "body": %s,
                  "httpHeaders": [
                    %s
                  ],
                  "pathAndQuery": %s
                }
                """.formatted(
                quote("GET"),
                quote("https://localhost:8080"),
                null,
                quote(
                        new String[]{
                                "Content-Type: application/json",
                                "User-Agent: Javazilla/99.99",
                        }
                ),
                quote("/message")
        );
        assertEquals(parser.parse(JSON), "GET https://localhost:8080/message");
    }

    private String quote(String str) {
        return String.format("\"%s\"", str);
    }

    private String quote(String[] arr) {
        return Arrays.stream(arr).map(this::quote).collect(Collectors.joining(","));
    }

}
