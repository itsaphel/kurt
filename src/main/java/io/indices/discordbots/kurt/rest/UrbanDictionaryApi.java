package io.indices.discordbots.kurt.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import io.indices.discordbots.kurt.Bot;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.json.JSONArray;

public class UrbanDictionaryApi {

    private static String BASE_URL = "https://api.urbandictionary.com/v0";
    private List<String> blacklistedWords = new ArrayList<>();

    public Optional<String> getDefinition(String term) {
        String definition = null;

        try {
            HttpResponse<JsonNode> response = request("define", new String[]{"term", term});

            if (response.getStatus() == 200) {
                JSONArray array = response.getBody().getObject()
                  .getJSONArray("list");

                if (!array.isNull(0)) {
                    definition = array
                      .getJSONObject(0)
                      .getString("definition");
                }
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(definition);
    }

    public void loadBlacklistedWords() throws FileNotFoundException, IOException {
        try (Stream<String> stream = Files.lines(Bot.currentDir.resolve("urbanblacklist"))) {
            stream.forEach(s -> blacklistedWords.add(s));
        }
    }

    public List<String> getBlacklistedWords() {
        return blacklistedWords;
    }

    private HttpResponse<JsonNode> request(String requestUri, String[]... queries) throws UnirestException {
        GetRequest request = Unirest.get(BASE_URL + "/" + requestUri);

        Arrays.stream(queries).forEach(query -> request.queryString(query[0], query[1]));

        return request.asJson();
    }
}
