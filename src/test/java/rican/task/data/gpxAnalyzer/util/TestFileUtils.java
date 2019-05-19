package rican.task.data.gpxAnalyzer.util;

import com.google.common.base.Charsets;
import org.springframework.mock.web.MockMultipartFile;
import org.synchronoss.cloud.nio.multipart.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Charsets.UTF_8;

public class TestFileUtils {

    private static final String OK_FILE = "gpx/test_data_ok.gpx";
    private static final String CORRUPTED_FILE = "gpx/test_data_corrupted.gpx";
    private static final String EMPTY_FILE = "gpx/test_data_without_nodes.gpx";
    private static final String SUCCESS_RESPONSE = "hereapi/success.json";

    private TestFileUtils() { }

    public static MockMultipartFile createOkFile() {
        return createMultipartFile(OK_FILE);
    }

    public static MockMultipartFile createCorruptedFile() {
        return createMultipartFile(CORRUPTED_FILE);
    }

    public static MockMultipartFile createFileWithoutWaypoints() {
        return createMultipartFile(EMPTY_FILE);
    }

    public static String createSuccessHereApiResponse() {
        try {
            return IOUtils.inputStreamAsString(loadTestingFile(SUCCESS_RESPONSE), UTF_8.displayName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static MockMultipartFile createMultipartFile(String name) {
        try {
            return new MockMultipartFile(name, loadTestingFile(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream loadTestingFile(String fileName) {
        return TestFileUtils.class.getClassLoader().getResourceAsStream(fileName);
    }
}