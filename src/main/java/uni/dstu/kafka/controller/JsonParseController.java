package uni.dstu.kafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uni.dstu.kafka.dto.JsonDto;
import uni.dstu.kafka.service.HttpClientService;
import uni.dstu.kafka.service.JsonParserService;

@RestController
@RequestMapping("json")
public class JsonParseController {

    @Autowired
    private JsonParserService parser;
    @Autowired
    private HttpClientService httpClient;

    @GetMapping
    public String echo() {
        return "echo";
    }

    /*
    Так как в задании требуется выполнить парсинг вручную, приходится использовать нестандартный подход передачи
    JSON'а в виде строки в параметре GET-запроса. GET-запрос используется для упрощения тестирования и в реальном
    приложении не должен использоваться с этими целями.

    Пример передачи самого простого JSON'а:
    http://localhost:8080/json/process?json=%7B%22requestMethod%22:%22GET%22,%22url%22:%22http://localhost:8080%22%7D
    */
    @GetMapping("process")
    public String processJson(@RequestParam(value = "json") String json) throws JsonProcessingException {
        JsonDto jsonDto = parser.parse(json);
        return httpClient.request(jsonDto);
    }
}