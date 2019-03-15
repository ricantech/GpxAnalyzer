package rican.task.data.gpxAnalyzer.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.service.closeplaces.RestClient;
import rican.task.data.gpxAnalyzer.service.converter.DomainConverter;
import rican.task.data.gpxAnalyzer.exception.PathNotFoundException;
import rican.task.data.gpxAnalyzer.model.ClosePlace;
import rican.task.data.gpxAnalyzer.model.Node;
import rican.task.data.gpxAnalyzer.model.Path;
import rican.task.data.gpxAnalyzer.repository.PathRepository;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class PathService {

    private final DomainConverter domainConverter;
    private final PathRepository pathRepository;
    private final RestClient closePlacesRestClient;

    public PathService(DomainConverter domainConverter, PathRepository pathRepository,
                       RestClient closePlacesRestClient) {
        this.domainConverter = domainConverter;
        this.pathRepository = pathRepository;
        this.closePlacesRestClient = closePlacesRestClient;
    }

    @Transactional(readOnly = true)
    public List<Path> getAllPaths() {
        return newArrayList(pathRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Path getPathDetails(Long id) {
        return pathRepository.findById(id).orElseThrow(() -> new PathNotFoundException(id));
    }

    @Transactional
    public Path process(MultipartFile file) {
        Path result = domainConverter.convert(file);
        updateWithClosePlaces(result);
        pathRepository.save(result);
        return result;
    }

    private void updateWithClosePlaces(Path path) {
        Node lastNode = path.getLastNode();
        List<ClosePlace> closePlaces = closePlacesRestClient.getClosePlaces(lastNode);
        lastNode.setClosePlaces(closePlaces);
    }
}