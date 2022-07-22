package spark;


import com.example.Data;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KafkaSpark {

    public static void main(String[] args) throws InterruptedException, StreamingQueryException {

        // Create a local StreamingContext and batch interval of 10 second
        SparkConf conf = new SparkConf()./*setMaster("local").*/setAppName("Kafka Spark Integration");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(6));


        // Create regex to parse records
        String pattern = "version:\\s\"(\\d+)\"\\nname:\\s\"(.+)\"\\ntimestamp:\\s(\\d+)(\\nphone_id:\\s\"(\\d+)\")?(\\nlon:\\s(\\d+))?(\\nlat:\\s(\\d+))?";
        Pattern p = Pattern.compile(pattern);

        //Define Kafka parameter
        Map<String, Object> kafkaParams = new HashMap<String, Object>();
//        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("bootstrap.servers", "172.17.80.26:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", KafkaProtobufDeserializer.class);
//        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "0");
//        kafkaParams.put("schema.registry.url", "http://localhost:8081");
        kafkaParams.put("schema.registry.url", "http://172.17.80.26:8081");

        // Automatically reset the offset to the earliest offset
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", false);

        //Define a list of Kafka topic to subscribe
        Collection<String> topics = Arrays.asList("data-tracking-hoangnlv");


        // Consume protobuf data from Kafka
        JavaInputDStream<ConsumerRecord<String, Data.DataTracking>> stream = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaParams)
        );

        JavaDStream<String> mess = stream.map((Function<ConsumerRecord<String, Data.DataTracking>, String>) kafkaRecord -> String.valueOf(((kafkaRecord.value()))));


//      Convert RDDs of the words DStream to DataFrame and save
        mess.foreachRDD((rdd, time) -> {
            SparkSession spark = JavaSparkSessionSingleton.getInstance(rdd.context().getConf());

            // Convert JavaRDD[String] to JavaRDD[bean class] to DataFrame
            JavaRDD<Record> rowRDD = rdd.map((Function<String, Record>) chunk -> {
                Matcher m = p.matcher(chunk);
                Record record = new Record();
                if (m.find()) {
                    record.setVersion(m.group(1));
                    record.setName(m.group(2));

                    LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(m.group(3))), TimeZone.getDefault().toZoneId());
                    record.setYear(timestamp.getYear());
                    record.setMonth(timestamp.getMonthValue());
                    record.setDay(timestamp.getDayOfMonth());
                    record.setHour(timestamp.getHour());

                    record.setPhone_id(m.group(5));
                    record.setLon(m.group(7) == null || m.group(7).isEmpty() ? 0 : Long.parseLong(m.group(7)));
                    record.setLat(m.group(9) == null || m.group(9).isEmpty() ? 0 : Long.parseLong(m.group(9)));
                }
                return record;
            });

            Dataset<Row> wordsDataFrame = spark.createDataFrame(rowRDD, Record.class);
            wordsDataFrame.show();

            wordsDataFrame.write()
                    .mode("append")
                    .option("compression", "snappy")
                    .option("checkpointLocation", "/user/hoangnlv/data_tracking/checkpoint")
                    .format("parquet")
                    .partitionBy("year", "month", "day", "hour")
//                    .save("./output/data_tracking.parquet");
                    .save("/user/hoangnlv/data_tracking/output/data_tracking.parquet");
        });


        // Start the computation
        jssc.start();
        jssc.awaitTermination();
    }

}

/**
 * Lazily instantiated singleton instance of SparkSession
 */
class JavaSparkSessionSingleton {
    private static transient SparkSession instance = null;

    public static SparkSession getInstance(SparkConf sparkConf) {
        if (instance == null) {
            instance = SparkSession
                    .builder()
                    .config(sparkConf)
                    .getOrCreate();
        }
        return instance;
    }
}