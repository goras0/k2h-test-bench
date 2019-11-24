package se.test.k2htestbench;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import se.test.k2htestbench.domain.User;

@Getter
@Builder
@ToString
public class TestCase {
    String name;
    User inData;
    String response;
    String result;
}
