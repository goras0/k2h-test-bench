package se.test.k2htestbench.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.test.k2htestbench.HubClient;
import se.test.k2htestbench.TestCase;
import se.test.k2htestbench.domain.User;

@Slf4j
@Service
public class Producer {

    //@Value("${app.topic.input}")
    private String topicIn = "users";

    ObjectMapper objectMapper = new ObjectMapper();

    final KafkaTemplate<Object, Object> template;

    @Autowired
    HubClient hubClient;

    @Autowired
    public Producer(KafkaTemplate<Object, Object> template) {
        this.template = template;
    }

    public void doTest(TestCase tc){
        // send data to input channel
        send(tc.getInData());
    }

    public void send(User user) {
        log.info("sending message='{}' to topic='{}'", user, topicIn);
        template.send(user);
    }

}
