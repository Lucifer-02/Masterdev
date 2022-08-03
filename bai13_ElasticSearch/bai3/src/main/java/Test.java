import org.springframework.data.elasticsearch.client.ClientConfiguration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String[] input = {"a", "à", "A", "À", "Nguyễn Lê"};

        for (String e : input) {
            System.out.println(Arrays.toString(e.toCharArray()));
        }

    }

}
