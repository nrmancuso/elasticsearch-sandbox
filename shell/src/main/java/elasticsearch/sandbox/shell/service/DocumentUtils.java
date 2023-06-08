package elasticsearch.sandbox.shell.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import elasticsearch.sandbox.shell.docs.ExoPlanetDocument;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class DocumentUtils {

    private DocumentUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<ExoPlanetDocument> generateExoPlanetDocuments(String filename) {
        final CsvMapper csvMapper = new CsvMapper();
        final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
        final ObjectReader objectReader = csvMapper
                .readerFor(ExoPlanetDocument.class)
                .with(csvSchema);

        final List<ExoPlanetDocument> exoPlanetDocuments = new ArrayList<>();
        try (Reader reader = new FileReader(filename, StandardCharsets.UTF_8)) {
            final MappingIterator<ExoPlanetDocument> mi = objectReader
                    .readValues(reader);
            while (mi.hasNext()) {
                final ExoPlanetDocument current = mi.next();
                exoPlanetDocuments.add(current);
                log.info("Current: {}", current);
            }
        } catch (IOException e) {
            log.error("Error reading file: {}", e.getMessage());
        }
        return exoPlanetDocuments;
    }
}
