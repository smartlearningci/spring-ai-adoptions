package smart.spring.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import javax.sql.DataSource;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;

import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;

@SpringBootApplication
public class AdoptionsApplication {
  public static void main(String[] args) {
    SpringApplication.run(AdoptionsApplication.class, args);
  }

  @Bean
  PromptChatMemoryAdvisor promptChatMemoryAdvisor(DataSource ds) {
    // Se preferires, podes @Autowired o JdbcChatMemoryRepository diretamente
    JdbcChatMemoryRepository repo =
        JdbcChatMemoryRepository.builder().dataSource(ds).build();

    ChatMemory mem = MessageWindowChatMemory
        .builder()
        .chatMemoryRepository(repo)
        .maxMessages(10)
        .build();

    return PromptChatMemoryAdvisor.builder(mem).build();
  }

 
}
