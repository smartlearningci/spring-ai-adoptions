package smart.spring.ai;

import org.springframework.web.bind.annotation.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;

@RestController
public class AdoptionsController {

  private final ChatClient ai;

  public AdoptionsController(ChatClient.Builder builder, PromptChatMemoryAdvisor mem) {
    this.ai = builder.defaultAdvisors(mem).build();
  }

  @GetMapping("/{user}/assistant")
  public String ask(@PathVariable String user, @RequestParam String question) {
    return ai.prompt()
        .user(question)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
        .call()
        .content();
  }
}
