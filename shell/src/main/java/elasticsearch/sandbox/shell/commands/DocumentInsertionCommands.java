package elasticsearch.sandbox.shell.commands;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import elasticsearch.sandbox.shell.docs.ExoPlanetDocument;
import lombok.extern.log4j.Log4j2;

@ShellComponent
@Log4j2
public class DocumentInsertionCommands {

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

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
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

        log.info("ExoPlanetDocuments: {}", exoPlanetDocuments);
    }
}
