import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadCustomers implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ReadCustomers.class);
    private final String fileLocation;
    private final String topicName;
    private final KafkaProducer<String, String> producer;

    private final boolean ignoreHeader;

    ReadCustomers(KafkaProducer<String, String> producer, String topicName, String fileLocation, boolean ignoreHeader) {
        this.fileLocation = fileLocation;
        this.topicName = topicName;
        this.producer = producer;
        this.ignoreHeader = ignoreHeader;
    }

    @Override
    public void run() {
        logger.info("Start Processing " + fileLocation);
        File file = new File(fileLocation);
        int counter = 0;

        String header = "";
        try (Scanner scanner = new Scanner(file)) {
            if (!ignoreHeader) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                producer.send(new ProducerRecord<>(topicName, null, line));
                counter++;
            }
            logger.info("Finished Sending " + counter + "messages from " + fileLocation);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}