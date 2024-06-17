package uni.dstu.kafka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uni.dstu.kafka.service.CacheService;

import java.util.List;

@RestController
@RequestMapping("/json")
@RequiredArgsConstructor
public class JsonParseController {
    private final CacheService cacheService;

    @GetMapping("/echo")
    public String echo() {
        return "echo";
    }

    @GetMapping
    public List<String> get_cache() {
        return cacheService.getCacheEntries();
    }
    @DeleteMapping
    public void delete(@RequestParam int id){ cacheService.deleteCacheEntry(id); }
    /*
    Ввиду специфики изначальных требований, не представляется возможным связать
    методы POST, PATCH и PUT с их реализацией. А именно: назначение приложения
    заключается в приёме сообщений из Kafka. Представляется затруднительным связать
    это с REST методами POST, PATCH и PUT.
     */
    @PostMapping
    public String post(){ return "post"; }
    @PatchMapping
    public String patch(){ return "patch"; }
    @PutMapping
    public String put(){ return "put"; }

}