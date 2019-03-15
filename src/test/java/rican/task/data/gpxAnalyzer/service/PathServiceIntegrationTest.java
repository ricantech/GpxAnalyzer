package rican.task.data.gpxAnalyzer.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.AbstractIntegrationTest;
import rican.task.data.gpxAnalyzer.model.Path;
import rican.task.data.gpxAnalyzer.repository.PathRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static rican.task.data.gpxAnalyzer.util.TestFileUtils.createOkFile;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PathServiceIntegrationTest extends AbstractIntegrationTest {

    private static final int EXPECTED_NUMBER_OF_NODES = 2;
    private static final int EXPECTED_NUMBER_OF_CLOSE_PLACES = 20;

    @Autowired
    private PathService pathService;

    @Autowired
    private PathRepository pathRepository;

    @Test
    @Transactional
    public void shouldProcessMultiPartFileAndStoreIntoDb() {
        MultipartFile okFile = createOkFile();

        Path result = pathService.process(okFile);

        assertPath(result);
        List<Path> paths = Lists.newArrayList(pathRepository.findAll());
        assertEquals(1, paths.size());
        assertPath(paths.get(0));
    }

    private void assertPath(Path path) {
        assertEquals(EXPECTED_NUMBER_OF_NODES, path.getNodes().size());
        assertEquals(EXPECTED_NUMBER_OF_CLOSE_PLACES, path.getLastNode().getClosePlaces().size());
        assertEquals(path.getStartNode(), path.getNodes().get(0));
        assertEquals(path.getLastNode(), path.getNodes().get(1));
    }
}