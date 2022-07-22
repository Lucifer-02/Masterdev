package kafka;

import com.example.Data;
import com.github.javafaker.Faker;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SendKafkaProto {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String bootstrapServers = "127.0.0.1:9092";

        Properties props = new Properties();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.80.26:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class);

//        props.put("schema.registry.url", "http://localhost:8081");
        props.put("schema.registry.url", "http://172.17.80.26:8081");

        KafkaProducer<String, Data.DataTracking> producer = new KafkaProducer<>(props);

        // Specify Topic
        String topic = "data-tracking-hoangnlv";
        Faker faker = new Faker();

//        while (true) {
        for (int i = 0; i < /*faker.number().numberBetween(1, 20)*/ 10; i++) {
            Data.DataTracking trackData = Data.DataTracking.newBuilder()
                    .setVersion(String.valueOf(faker.number().numberBetween(1,100)))
                    .setName(faker.name().fullName())
                    .setTimestamp(faker.date().past(100, TimeUnit.DAYS).getTime())
                    .setPhoneId(String.valueOf(faker.number().randomNumber()))
                    .setLon(faker.number().numberBetween(0, 9999))
                    .setLat(faker.number().numberBetween(0, 9999))
                    .build();

            producer.send(new ProducerRecord<String, Data.DataTracking>(topic, "Data Tracking", trackData)).get();
        }
        TimeUnit.SECONDS.sleep(2);
//        }
        producer.flush();
        producer.close();
        System.out.println("Sent Data Successfully");
    }
}