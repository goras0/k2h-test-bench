package se.test.k2htestbench;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import se.test.k2htestbench.domain.User;

@Getter
@Builder
@ToString
public class TestCase {
    // the test case name
    String name;

    // data to send to pipeline under test
    User inData;

    // response from hub stored state
    String response;

    // result of the test case
    String result;
}
