package uni.dstu.kafka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("json")
public class JsonParseController {
    @GetMapping
    public String echo() {
        return "echo";
    }
}