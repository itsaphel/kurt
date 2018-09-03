package io.indices.discordbots.kurt.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Named;
import org.json.JSONArray;

public class UrbanDictionaryApi {

    @Inject
    @Named("RunDir")
    private Path currentDir;

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

    public void loadBlacklistedWords() throws IOException {
        try (Stream<String> stream = Files.lines(currentDir.resolve("urbanblacklist"))) {
            stream.forEach(s -> blacklistedWords.add(s));
        }
    }

    public List<String> getBlacklistedWords() {
        return blacklistedWords;
    }

    private HttpResponse<JsonNode> request(String requestUri, String[]... queries)
      throws UnirestException {
        GetRequest request = Unirest.get(BASE_URL + "/" + requestUri);

        Arrays.stream(queries).forEach(query -> request.queryString(query[0], query[1]));

        return request.asJson();
    }
}
