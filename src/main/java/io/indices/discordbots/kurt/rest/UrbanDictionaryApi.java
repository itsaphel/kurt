package io.indices.discordbots.kurt.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import java.util.Arrays;
import java.util.Optional;

public class UrbanDictionaryApi {

    private static String BASE_URL = "https://api.urbandictionary.com/v0";

    public Optional<String> getDefinition(String term) {
        String definition = null;

        try {
            HttpResponse<JsonNode> response = request("define", new String[]{"term", term});
            if (response.getStatus() == 200) {
                definition = response.getBody().getObject()
                  .getJSONArray("list")
                  .getJSONObject(0)
                  .getString("definition");
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(definition);
    }

    private HttpResponse<JsonNode> request(String requestUri, String[]... queries) throws UnirestException {
        HttpRequestWithBody request = Unirest.post(BASE_URL + "/" + requestUri);

        Arrays.stream(queries).forEach(query -> request.queryString(query[0], query[1]));

        return request.asJson();
    }
}
