package smart.spring.ai;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;

import java.util.ArrayList;
import java.util.List;

@Service
public class DogIngestService {
  private final DogRepository repo;
  private final VectorStore vs;

  public DogIngestService(DogRepository repo, VectorStore vs) {
    this.repo = repo; this.vs = vs;
  }

  @Transactional
  public void ingestAll() {
    List<Dog> dogs = repo.findAll();           // <- List<Dog>, não Object
    List<Document> docs = new ArrayList<>();
    for (Dog d : dogs) {
      docs.add(new Document(
        d.name() + " - " + d.description(),
        java.util.Map.of("id", String.valueOf(d.id()))
      ));
    }
    vs.add(docs); // usa embeddings (Ollama) e grava no PGVector
  }
}
