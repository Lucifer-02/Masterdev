import com.opencsv.CSVWriter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(ReadCustomers.class);

    public static void run() {
        Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "demo");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.80.20:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton("quickstart-events"));

        String path = "data/out.csv";
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        consumer.commitAsync();
        filterAndWrite2File(records, path);
        consumer.close();

    }

    public static void filterAndWrite2File(ConsumerRecords<String, String> records, String path) {

        // Storage customers data
        List<String[]> data = new ArrayList<String[]>();

        boolean check = true;
        for (ConsumerRecord<String, String> record : records) {

            // Storage header
            String[] line = record.value().split(",");
            if (check) {
                check = false;
                System.out.println(record.value());
                data.add(line);
                continue;
            }

            // storage customers details satisfy
            if (Integer.parseInt(line[2]) < 20 && Integer.parseInt(line[1]) > 100) {
                System.out.println(record.value());
                data.add(line);
            }
        }

        // Write to file
        try {
            File file = new File(path);

            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeAll(data);

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
