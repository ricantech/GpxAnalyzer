package rican.task.data.gpxAnalyzer.domain.repository;

import org.springframework.data.repository.CrudRepository;
import rican.task.data.gpxAnalyzer.domain.model.Path;

public interface PathRepository extends CrudRepository<Path, Long> { }
