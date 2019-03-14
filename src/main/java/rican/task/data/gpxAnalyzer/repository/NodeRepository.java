package rican.task.data.gpxAnalyzer.repository;

import org.springframework.data.repository.CrudRepository;
import rican.task.data.gpxAnalyzer.model.Node;

public interface NodeRepository extends CrudRepository<Node, Long> {
}
