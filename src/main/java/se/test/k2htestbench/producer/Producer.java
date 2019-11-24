package se.test.k2htestbench.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.test.k2htestbench.service.HubClient;
import se.test.k2htestbench.TestCase;
import se.test.k2htestbench.domain.User;

@Slf4j
@Service
public class Producer {

   // @Value("${app.topic.input:users}")
    private String topicIn = "users";

    ObjectMapper objectMapper = new ObjectMapper();

    final KafkaTemplate<Object, Object> template;

    @Autowired
    HubClient hubClient;

    @Autowired
    public Producer(KafkaTemplate<Object, Object> template) {
        this.template = template;
    }

    public void send(User user) {
        log.info("sending message='{}' to topic='{}'", user, topicIn);
        template.send(topicIn, user);
    }

}
