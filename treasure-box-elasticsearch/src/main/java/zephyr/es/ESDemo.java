package zephyr.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ESDemo {

    public static final String INDEX = "posts";
    public static final String ID = "1";

    public static void main(String[] args) {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        );
        try (RestHighLevelClient client = new RestHighLevelClient(restClientBuilder)) {
            indexAPI(client);
            getAPI(client);
            getResourceAPI(client);
            existAPI(client);
            deleteAPI(client);
            indexAPI(client);
            updateAPI(client);
            getResourceAPI(client);
            // 分词向量
            termVectorsAPI(client);
            // 批量更新
            bulkAPI(client);
            // 批量查询
            multiGetAPI(client);
            // 复制文档
            reindexAPI(client);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    private static void reindexAPI(RestHighLevelClient client) throws IOException {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices(INDEX);
        request.setDestIndex(INDEX + "_new");
        BulkByScrollResponse bulkByScrollResponse =
                client.reindex(request, RequestOptions.DEFAULT);
        log.info("reindexAPI, {}", bulkByScrollResponse);
    }

    private static void multiGetAPI(RestHighLevelClient client) throws IOException {
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        multiGetRequest.add(new MultiGetRequest.Item(INDEX, "2"));
        multiGetRequest.add(new MultiGetRequest.Item(INDEX, "3"));
        MultiGetResponse response = client.mget(multiGetRequest, RequestOptions.DEFAULT);
        log.info("multiGetAPI");
        for (MultiGetItemResponse multiGetItemResponse : response.getResponses()) {
            GetResponse getResponse = multiGetItemResponse.getResponse();
            if (getResponse.isExists()) {
                String sourceAsString = getResponse.getSourceAsString();
                log.info("\tid={}, {}", multiGetItemResponse.getId(),sourceAsString);
            } else {
                log.info("\t{} is NOT exist", multiGetItemResponse.getId());
            }
        }
    }

    private static void bulkAPI(RestHighLevelClient client) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest(INDEX).id("2")
                .source(XContentType.JSON,"field", "foo", "postDate", new Date(), "message", "agaojqwoi", "user", "zephyr"));
        bulkRequest.add(new IndexRequest(INDEX).id("3")
                .source(XContentType.JSON,"field", "bar", "postDate", new Date()));
        bulkRequest.add(new IndexRequest(INDEX).id("4")
                .source(XContentType.JSON,"field", "baz", "postDate", new Date()));
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("bulkAPI, status: {}, hasFailures: {}", bulkResponse.status(), bulkResponse.hasFailures());
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();

            switch (bulkItemResponse.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    log.info("\t {}", indexResponse);
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    log.info("\t {}", updateResponse);
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    log.info("\t {}", deleteResponse);
            }
        }
    }

    private static void termVectorsAPI(RestHighLevelClient client) throws IOException {
        TermVectorsRequest termVectorsRequest = new TermVectorsRequest(INDEX, ID);
        termVectorsRequest.setFields("message");
        TermVectorsResponse termVectorsResponse = client.termvectors(termVectorsRequest, RequestOptions.DEFAULT);

        log.info("termVectorsAPI, fount: {}", termVectorsResponse.getFound());
        for (TermVectorsResponse.TermVector tv : termVectorsResponse.getTermVectorsList()) {
            String fieldName = tv.getFieldName();
            int docCount = tv.getFieldStatistics().getDocCount();
            long sumTotalTermFreq =
                    tv.getFieldStatistics().getSumTotalTermFreq();
            long sumDocFreq = tv.getFieldStatistics().getSumDocFreq();
            log.info("\tfieldName={}, docCount={}, sumTotalTermFreq={}, sumDocFreq={}", fieldName, docCount, sumTotalTermFreq, sumDocFreq);
            if (tv.getTerms() != null) {
                List<TermVectorsResponse.TermVector.Term> terms =
                        tv.getTerms();
                for (TermVectorsResponse.TermVector.Term term : terms) {
                    String termStr = term.getTerm();
                    int termFreq = term.getTermFreq();
                    Integer docFreq = term.getDocFreq();
                    Long totalTermFreq = term.getTotalTermFreq();
                    Float score = term.getScore();
                    log.info("\t\ttermStr={}, termFreq={}, docFreq={}, totalTermFreq={}, score={}", termStr, termFreq, docFreq, totalTermFreq, score);
                    if (term.getTokens() != null) {
                        List<TermVectorsResponse.TermVector.Token> tokens =
                                term.getTokens();
                        for (TermVectorsResponse.TermVector.Token token : tokens) {
                            int position = token.getPosition();
                            int startOffset = token.getStartOffset();
                            int endOffset = token.getEndOffset();
                            String payload = token.getPayload();
                            log.info("\t\t\tposition={}, startOffset={}, endOffset={}, payload={}", position, startOffset, endOffset, payload);
                        }
                    }
                }
            }
        }
    }

    private static void updateAPI(RestHighLevelClient client) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(
                INDEX,
                ID);
        String jsonString = "{" +
                "\"updated\":\"2017-01-01\"," +
                "\"reason\":\"daily update\"" +
                "}";
        updateRequest.doc(jsonString, XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        log.info("updateAPI, {}", updateResponse);
    }

    private static void deleteAPI(RestHighLevelClient client) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(
                INDEX,
                ID);
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("deleteAPI, {}", deleteResponse);
    }

    private static void existAPI(RestHighLevelClient client) throws IOException {
        GetRequest getRequest = new GetRequest(
                INDEX,
                ID);
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        log.info("existAPI, {}", exists);
    }

    private static void getResourceAPI(RestHighLevelClient client) throws IOException {
        GetSourceRequest getSourceRequest = new GetSourceRequest(
                INDEX,
                ID);
        GetSourceResponse getSourceResponse =
                client.getSource(getSourceRequest, RequestOptions.DEFAULT);
        log.info("getResourceAPI, {}", getSourceResponse);
    }

    private static void getAPI(RestHighLevelClient client) throws IOException {
        GetRequest getRequest = new GetRequest(
                INDEX,
                ID);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        log.info("getAPI, {}", getResponse);
    }

    private static void indexAPI(RestHighLevelClient client) throws IOException {
        IndexRequest request = new IndexRequest(INDEX);
        request.id(ID);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");

        request.source(jsonMap);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        log.info("indexAPI, {}", indexResponse);
    }

}
