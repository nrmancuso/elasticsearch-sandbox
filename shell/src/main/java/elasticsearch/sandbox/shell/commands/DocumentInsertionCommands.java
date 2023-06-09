package elasticsearch.sandbox.shell.commands;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import elasticsearch.sandbox.shell.docs.ExoPlanetDocument;
import elasticsearch.sandbox.shell.service.BulkInsertionService;
import elasticsearch.sandbox.shell.service.DocumentUtils;
import elasticsearch.sandbox.shell.service.Index;
import lombok.extern.log4j.Log4j2;

@ShellComponent
@Log4j2
public class DocumentInsertionCommands {

    private final ObjectMapper objectMapper;
    private final BulkInsertionService bulkInsertionService;

    public DocumentInsertionCommands(ObjectMapper objectMapper,
                                     BulkInsertionService bulkInsertionService) {
        this.objectMapper = objectMapper;
        this.bulkInsertionService = bulkInsertionService;
    }

    /**
     * Insert documents from csv into the index.
     * To run command: insert-docs --index index_name --filename filename.csv
     *
     * @param index    the index o insert the data into
     * @param filename the name of the input file
     */
    @ShellMethod("Insert documents from csv into the index")
    public void insertDocs(String index, String filename) {
        log.info("Inserting documents from csv");
        log.info("Index: {}", index);
        log.info("Filename: {}", filename);

        final List<ExoPlanetDocument> exoPlanetDocuments =
                DocumentUtils.generateExoPlanetDocuments(filename);
        log.info("ExoPlanetDocuments: {}", exoPlanetDocuments);

        final List<IndexRequest> indexRequests = exoPlanetDocuments.stream()
                .map(this::toIndexRequest)
                .toList();

        log.info("IndexRequests: {}", indexRequests);
        bulkInsertionService.insertDocuments(indexRequests);
    }

    private IndexRequest toIndexRequest(ExoPlanetDocument exoPlanetDocument) {
        return new IndexRequest(Index.EXOPLANET.name().toLowerCase(Locale.ROOT))
                .id(exoPlanetDocument.getGaiaId().replace(" ", "_"))
                .source(objectMapper.convertValue(exoPlanetDocument,
                                new TypeReference<Map<String, Object>>() {}));
    }
}
