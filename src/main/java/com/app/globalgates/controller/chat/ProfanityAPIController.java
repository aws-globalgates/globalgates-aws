package com.app.globalgates.controller.chat;

import com.app.globalgates.dto.chat.ProfanityCheckResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/api/v1/chat")
public class ProfanityAPIController {
    private final WebClient webClient;

    public ProfanityAPIController(@Value("${profanity.api.base-url}") String profanityApiBaseUrl) {
        this.webClient = WebClient.create(profanityApiBaseUrl);
    }

    @PostMapping("/profanity-check")
    @ResponseBody
    public Mono<ProfanityCheckResponseDTO> profanityCheck(@RequestBody Map<String, String> body){
        String message = body.get("message");
        log.info("message : {}", message);

        return webClient.post()
                .uri("/api/profanity/check")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ProfanityCheckResponseDTO.class); // 비동기로 결과를 받음
    }
}
