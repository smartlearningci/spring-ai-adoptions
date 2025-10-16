package smart.spring.ai;

import org.springframework.web.bind.annotation.*;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;

import java.util.List;

@RestController
@RequestMapping("/rag")
public class RagController {
  private final DogIngestService svc;
  private final VectorStore vs;
  private final ChatClient ai;

  public RagController(DogIngestService svc, VectorStore vs, ChatClient.Builder builder) {
    this.svc = svc;
    this.vs = vs;
    this.ai = builder.build();
  }

  @PostMapping("/ingest")
  public void ingest() { svc.ingestAll(); }

  @GetMapping("/ask")
  public String ask(@RequestParam String q) {
    // Busca similaridade (usa topK por defeito do VectorStore)
    List<Document> hits = vs.similaritySearch(q);

    // Limita a 3 manualmente (evita SearchRequest)
    int k = Math.min(3, hits.size());
    StringBuilder ctx = new StringBuilder("Contexto sobre cães:\n");
    for (int i = 0; i < k; i++) {
      ctx.append("- ").append(hits.get(i).getFormattedContent()).append("\n");
    }

    return ai.prompt()
      .system("Responde curto e correto usando o contexto se ajudar.\n" + ctx)
      .user(q)
      .call().content();
  }
}
