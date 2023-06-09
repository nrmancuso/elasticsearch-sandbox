package elasticsearch.sandbox.shell.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ApplicationConfiguration {

    private static final int PORT = 9200;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", PORT, "http"))
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
