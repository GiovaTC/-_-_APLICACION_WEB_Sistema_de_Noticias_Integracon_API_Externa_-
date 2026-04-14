package com.noticias.app.client;

import com.noticias.app.model.Noticia;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

// CLIENTE API / .
@Service
public class NewsApiClient {

    private final String API_KEY = "20db8cff73f14151b2f1b07be029bb3d";
    private final String BASE_URL = "https://newsapi.org";

    public List<Noticia> obtenerNoticias(String pais) {

        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .build();

        Map response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/top-headlines") // ✔ solo path
                        .queryParam("country", pais)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<Map<String, Object>> articles =
                (List<Map<String, Object>>) response.get("articles");

        return articles.stream().map(a -> {
            Noticia n = new Noticia();
            n.setTitulo((String) a.get("title"));
            n.setDescripcion((String) a.get("description"));
            n.setUrl((String) a.get("url"));
            n.setFuente(((Map) a.get("source")).get("name").toString());
            return n;
        }).toList();
    }
}
