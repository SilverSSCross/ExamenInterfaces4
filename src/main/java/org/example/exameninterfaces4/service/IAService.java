package org.example.exameninterfaces4.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class IAService {

    private final WebClient webClient;

    public IAService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer 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")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.REFERER, "http://localhost:8080")
                .build();
    }

    public String preguntar(String pregunta) {
        try {
            Map<String, Object> request = Map.of(
                    "model", "mistralai/mistral-7b-instruct:free",
                    "messages", List.of(Map.of("role", "user", "content", pregunta))
            );

            Map response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.value() == 401,
                            resp -> resp.bodyToMono(String.class)
                                    .defaultIfEmpty("Unauthorized")
                                    .flatMap(body -> Mono.error(new RuntimeException("401 Unauthorized: " + body))))
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) return "Respuesta vacía de la IA";

            Object choicesObj = response.get("choices");
            if (!(choicesObj instanceof List)) return "Sin `choices` en la respuesta";

            List choices = (List) choicesObj;
            if (choices.isEmpty()) return "Sin `choices` en la respuesta";

            Object firstChoice = choices.get(0);
            if (!(firstChoice instanceof Map)) return "Formato inesperado en `choices[0]`";

            Map firstMap = (Map) firstChoice;
            Object messageObj = firstMap.get("message");

            Map message;
            if (messageObj instanceof Map) {
                message = (Map) messageObj;
            } else if (firstMap.get("content") != null) {
                message = Map.of("content", firstMap.get("content"));
            } else {
                return "Sin `message` en `choices[0]`";
            }

            Object content = message.get("content");
            return content != null ? content.toString() : "Sin contenido en el mensaje";

        } catch (Exception e) {
            return "Error al consultar la IA: " + e.getMessage();
        }
    }

    public String recomendarProductos(String consulta) {
        String prompt = "Eres un asistente de compras. Basado en este stock: '" + consulta +
                "', recomienda que puedo hacer.";
        return preguntar(prompt);
    }
}

