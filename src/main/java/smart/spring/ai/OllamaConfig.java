package smart.spring.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ai.ollama.api.OllamaApi;

@Configuration
class OllamaConfig {

  @Bean
  OllamaApi ollamaApi(
      @Value("${spring.ai.ollama.base-url:http://localhost:11434}") String baseUrl,
      RestClient.Builder restClientBuilder,
      WebClient.Builder webClientBuilder
  ) {
    // Spring AI 1.x: usa o Builder estático
    return OllamaApi.builder()
        .baseUrl(baseUrl)
        .restClientBuilder(restClientBuilder)
        .webClientBuilder(webClientBuilder)
        .build();
  }
}
