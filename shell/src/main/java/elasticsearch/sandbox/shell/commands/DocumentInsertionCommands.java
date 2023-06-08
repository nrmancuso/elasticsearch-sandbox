package elasticsearch.sandbox.shell.commands;

import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import elasticsearch.sandbox.shell.docs.ExoPlanetDocument;
import elasticsearch.sandbox.shell.service.DocumentUtils;
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

        final List<ExoPlanetDocument> exoPlanetDocuments =
                DocumentUtils.generateExoPlanetDocuments(filename);
        log.info("ExoPlanetDocuments: {}", exoPlanetDocuments);
    }
}
