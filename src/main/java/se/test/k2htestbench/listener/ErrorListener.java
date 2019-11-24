package se.test.k2htestbench.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import se.test.k2htestbench.domain.User;

@Slf4j
@Service
public class ErrorListener {

   // @KafkaListener(id = "errorGroup", topics = "${app.topic.error}")
    public void listen(User data) {
        log.info("Received: " + data.toString());
    }
}
