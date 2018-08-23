package io.indices.discordbots.kurt.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import io.indices.discordbots.kurt.Bot;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WolframApi {

    private static String BASE_URL = "http://api.wolframalpha.com/v2";

    private Bot main;
    private String apiKey;
    private Logger logger = Logger.getLogger(WolframApi.class.getName());

    public WolframApi(Bot main, String apiKey) {
        this.main = main;
        this.apiKey = apiKey;
    }

    public Optional<String> getQueryImage(String inputString) {
        String queryImage = null;

        try {
            HttpResponse<JsonNode> response = request("query", new String[]{"input", inputString});
            if (response.getStatus() == 200) {
                queryImage = response.getBody()
                    .getObject().getJSONObject("queryresult")
                    .getJSONArray("pods")
                    .getJSONObject(1)
                    .getJSONArray("subpods")
                    .getJSONObject(0)
                    .getJSONObject("img")
                    .getString("src");
            }
        } catch (UnirestException e) {
            logger.log(Level.WARNING, "Error communicating with Wolfram API", e);
        }

        return Optional.ofNullable(queryImage);
    }

    private HttpResponse<JsonNode> request(String requestUri, String[]... queries)
        throws UnirestException {
        HttpRequestWithBody request = Unirest.post(BASE_URL + "/" + requestUri)
            .queryString("appid", apiKey)
            .queryString("output", "json");

        Arrays.stream(queries).forEach(query -> request.queryString(query[0], query[1]));

        return request.asJson();
    }

}
