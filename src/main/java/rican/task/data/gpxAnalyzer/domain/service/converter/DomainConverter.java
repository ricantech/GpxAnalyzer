package rican.task.data.gpxAnalyzer.domain.service.converter;

import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.domain.model.Path;

public interface DomainConverter {
    Path convert(MultipartFile file);
}