package smart.spring.ai;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.chat.client.ChatClient;

@SpringBootApplication
public class AdoptionsApplication {
  public static void main(String[] args) { SpringApplication.run(AdoptionsApplication.class, args); }
}

@Controller
@ResponseBody
class AdoptionsController {

  private final ChatClient ai;

  AdoptionsController(ChatClient.Builder ai) {
    this.ai = ai.build();
  }

  @GetMapping("/{user}/assistant")
  String ask(@PathVariable String user, @RequestParam String question) {
    return ai.prompt().user(question).call().content();
  }
}
