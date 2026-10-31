
package com.otto.fitIA.ia;

import tools.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class GroqClient {

    private final WebClient webClient;
    private final String model;

    public GroqClient(
            @Value("${groq.api-key}") String apiKey,
            @Value("${groq.base-url}") String baseUrl,
            @Value("${groq.model}") String model
    ) {
        this.model = model;

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + apiKey
                )
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    public String enviarMensagem(String mensagem) {

        Map<String, Object> request = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", mensagem
                        )
                ),
                "temperature", 0.7
        );

        JsonNode response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (response == null ||
                !response.has("choices") ||
                response.get("choices").isEmpty()) {

            throw new RuntimeException(
                    "A Groq não retornou uma resposta válida."
            );
        }

        JsonNode content = response
                .get("choices")
                .get(0)
                .get("message")
                .get("content");

        if (content == null || content.isNull()) {
            throw new RuntimeException(
                    "A resposta da Groq não contém conteúdo."
            );
        }

        return content.asString();
    }
}
