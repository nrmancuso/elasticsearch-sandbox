package elasticsearch.sandbox.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import elasticsearch.sandbox.shell.docs.ExoPlanetDocument;
import elasticsearch.sandbox.shell.service.DocumentUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DocumentGenerationTest {

    @Test
    public void testExoPlanetDocumentGeneration() {
        final List<ExoPlanetDocument> exoPlanetDocuments =
                DocumentUtils.generateExoPlanetDocuments(
                        "src/test/resources/test.csv");
        log.info("ExoPlanetDocuments: {}", exoPlanetDocuments);
        assertEquals(3, exoPlanetDocuments.size());
    }
}
