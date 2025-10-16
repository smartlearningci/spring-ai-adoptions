package smart.spring.ai;

import org.springframework.data.repository.ListCrudRepository;

public interface DogRepository extends ListCrudRepository<Dog, Integer> {}
