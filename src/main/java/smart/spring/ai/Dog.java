package smart.spring.ai;

import org.springframework.data.annotation.Id;

public record Dog(@Id Integer id, String name, String owner, String description) {}
