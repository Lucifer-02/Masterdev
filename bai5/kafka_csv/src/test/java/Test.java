import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    Logger logger = LoggerFactory.getLogger(Test.class);
    @org.junit.Test
    public void test1(){
        Producer.run();
        Consumer.run();
    }
}
