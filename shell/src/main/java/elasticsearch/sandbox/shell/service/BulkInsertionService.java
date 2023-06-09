package elasticsearch.sandbox.shell.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class BulkInsertionService {

    private final RestHighLevelClient elasticsearchClient;

    public BulkInsertionService(
            RestHighLevelClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    /**
     * Insert documents into the index.
     * @param indexRequests list of index requests to insert
     */
    public void insertDocuments(List<IndexRequest> indexRequests) {
        final BulkRequest bulkRequest = new BulkRequest();
        indexRequests.forEach(bulkRequest::add);
        log.info("BulkItemRequest: {}", bulkRequest);

        final BulkResponse bulkResponse;
        try {
            bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("Error inserting documents: {}", e.getMessage());
            throw new IllegalStateException(e);
        }

        if (bulkResponse.hasFailures()) {
            log.error("Error inserting documents: {}", bulkResponse.buildFailureMessage());
            throw new IllegalStateException(bulkResponse.buildFailureMessage());
        }

        log.info("Documents inserted successfully: {}",
                Arrays.stream(bulkResponse.getItems())
                        .map(BulkItemResponse::getId)
                        .collect(Collectors.toList()));
        log.info("Took: {}", bulkResponse.getTook());
        log.info("IngestTookInMillis: {}", bulkResponse.getIngestTookInMillis());
        log.info("Number of documents inserted: {}", bulkResponse.getItems().length);
    }
}
