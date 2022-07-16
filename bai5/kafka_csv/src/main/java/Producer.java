import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(ReadCustomers.class);

    public static void run() {

        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "demo");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.80.20:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        logger.info("Starting load...");

        String[] dataFiles = {"data/consumers.csv", "data/consumers1.csv"};
        String topicName = "quickstart-events";
        for (int i = 0; i < dataFiles.length; i++) {
            new ReadCustomers(producer, topicName, dataFiles[i], i == 0).run();
        }

        producer.close();
        logger.info("Finished Producer load Demo.");
    }
}