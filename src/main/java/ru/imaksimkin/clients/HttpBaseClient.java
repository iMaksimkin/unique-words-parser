package ru.imaksimkin.clients;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
public class HttpBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpBaseClient.class);

    /**
     * The method performs the get call using default http Client
     *
     * @param uri
     * @return htmlPage
     */
    @SneakyThrows
    public String getResponse(String uri) {
        String htmlPage = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
            //TODO add strategy for redirecting (301\302)
            response = httpclient.execute(httpGet);
            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                htmlPage = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
            }
        } catch (Exception ex) {
            logger.error("Failure while getting the response\n" + ex.getMessage(), ex);
            throw new RuntimeException(ex);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage(), ex);
                }
            }
        }
        return htmlPage;
    }
}

