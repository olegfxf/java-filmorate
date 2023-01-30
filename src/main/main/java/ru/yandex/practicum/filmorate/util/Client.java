package ru.yandex.practicum.filmorate.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    public static HttpResponse<String> http(String method, URI uri,
                                            String requestBody) throws IOException, InterruptedException {
        HttpRequest request = null;
        if (method.equals("GET"))
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(uri)
                    .header("content-type", "application/json")
                    .build();
        else if (method.equals("POST"))
            request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(uri)
                    .header("content-type", "application/json")
                    .build();
        else if (method.equals("PUT"))
            request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(uri)
                    .header("content-type", "application/json")
                    .build();
        else if (method.equals("DELETE")) {
            request = HttpRequest.newBuilder()
                    .DELETE()
                    .uri(uri)
                    .build();
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        return response;
    }
}
