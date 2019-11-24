package se.test.k2htestbench;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import se.test.k2htestbench.domain.User;
import se.test.k2htestbench.producer.Producer;
import se.test.k2htestbench.service.HubClient;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class K2hTestBenchApplication {
	static String TEST_CASE_PATH = "testcases";
	static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(K2hTestBenchApplication.class, args);
	}


	@Bean
	public ApplicationRunner runner(KafkaTemplate<Object, Object> template, HubClient hubClient) {
		return args -> {
			K2hTestBenchApplication app = new K2hTestBenchApplication();
			Producer producer = new Producer(template);
			try {
				// get all test cases
				List<URL> fileList = getResources(TEST_CASE_PATH);

				for(URL url : fileList){
					TestCase tc = getTestCase(url);
					log.info("======================================");
					log.info("Running test case : {}", tc.getName());

					// send data to pipeline under test
					producer.send(tc.getInData());

					// call hub to see result
					String response = hubClient.getData(tc);

					// Save response to file, ./output/<testcase>
					saveToFile("./output/"+tc.getName().replace(".", "-result."), response);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}

	public void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
			}
		}
	}

	public static List<URL> getResources(final String path) throws IOException {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (
				final InputStream is = loader.getResourceAsStream(path);
				final InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				final BufferedReader br = new BufferedReader(isr)) {
			return br.lines()
					.map(l -> path + "/" + l)
					.map(r -> loader.getResource(r))
					.collect(Collectors.toList());
		}
	}



	// read test case files
	private static TestCase getTestCase(final URL resource) throws IOException {
		File file = new File(resource.getFile());
		User user = objectMapper.readValue(file, User.class);
		TestCase tc =  TestCase.builder()
				.inData(user)
				.name(file.getName())
				.build();

		return tc;
	}

	private void saveToFile(String outfile, String data){
		// write to file
		try {
			PrintWriter writer = new PrintWriter(outfile, "UTF-8");
			writer.println(data);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
