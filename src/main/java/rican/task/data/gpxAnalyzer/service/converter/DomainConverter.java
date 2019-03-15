package rican.task.data.gpxAnalyzer.service.converter;

import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.model.Path;

public interface DomainConverter {
    Path convert(MultipartFile file);
}