package org.example.exameninterfaces4.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class IAService {

    private final WebClient webClient;

    public IAService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader("Authorization", "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") // Regístrate en openrouter.ai
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String preguntar(String pregunta) {
        try {
            Map<String, Object> request = Map.of(
                    "model", "meta-llama/llama-3-8b-instruct:free", // Modelo gratis
                    "messages", new Object[]{
                            Map.of("role", "user", "content", pregunta)
                    }
            );

            Map response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            Map choices = (Map) ((java.util.List) response.get("choices")).get(0);
            Map message = (Map) choices.get("message");

            return (String) message.get("content");

        } catch (Exception e) {
            return "Error al consultar la IA: " + e.getMessage();
        }
    }

    public String recomendarProductos(String consulta) {
        String prompt = "Eres un asistente de compras. Basado en esta consulta: '" + consulta +
                "', recomienda productos de nuestra tienda. Sé breve y específico.";
        return preguntar(prompt);
    }
}
