package se.test.k2htestbench;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HubClient {
    private static final String HUB_API_BASE_URL = "http://localhost:8080";

    private final WebClient webClient;

    public HubClient() {
        this.webClient = WebClient.builder()
                .baseUrl(HUB_API_BASE_URL)
                .build();
    }

    public String getData(TestCase tc) {
        return webClient.get().uri("/hub").retrieve().bodyToMono(String.class).block();
    }

}
