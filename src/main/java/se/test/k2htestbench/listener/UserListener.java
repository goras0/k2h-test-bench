package se.test.k2htestbench.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import se.test.k2htestbench.domain.User;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class UserListener {
    ObjectMapper objectMapper = new ObjectMapper();

    String outfile = "./output/result.txt";

    @KafkaListener(id = "userGroup", topics = "${app.topic.input}")
    public void listen(String data) {
        log.info("Received: " + data);
    }

    private void writeToFile(String data){
        Path path = Paths.get(outfile);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter( path, charset)) {
            writer.write(data, 0, data.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
