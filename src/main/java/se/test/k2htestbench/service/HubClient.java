package se.test.k2htestbench.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import se.test.k2htestbench.TestCase;

@Slf4j
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
        // wait 1 sec
 /*       try {
            Thread.currentThread().wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        String response = webClient.get().uri("/hub").retrieve().bodyToMono(String.class).block();
        log.info("Got response from hub: {}", response);
        return response;
    }

}
