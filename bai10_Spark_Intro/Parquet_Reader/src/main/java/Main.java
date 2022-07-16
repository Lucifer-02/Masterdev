import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
//                .config("spark.master", "local")
                .getOrCreate();


        Dataset<Row> parquetFileDF = spark.read().parquet("/hoangnlv/spark/input/big.parquet");
        parquetFileDF.createOrReplaceTempView("customers");

        // remove null device_model and duplicate
        spark.sql("SELECT DISTINCT device_model, user_id FROM customers WHERE device_model IS NOT NULL").createOrReplaceTempView("Table1");

        // remove null device_model and null button_id
        spark.sql("SELECT device_model, user_id, button_id FROM customers WHERE button_id IS NOT NULL AND device_model IS NOT NULL").createOrReplaceTempView("Table2");


        Dataset<Row> device_model_list_user = spark.sql(
                "SELECT device_model, COLLECT_LIST(user_id) as list_user_id FROM Table1 GROUP BY device_model");

        device_model_list_user.createOrReplaceTempView("List_Table");
        Dataset<Row> device_model_num_user = spark.sql(
                "SELECT device_model, SIZE(list_user_id) as count FROM List_Table");


        Dataset<Row> action_by_button_id = spark.sql("SELECT concat(user_id , '_', device_model) AS user_id_device_model, button_id , COUNT(*) as count " +
                "FROM Table2 GROUP BY user_id_device_model, button_id"
        );

        device_model_num_user.repartition(1).write().mode("overwrite").option("compression", "snappy").format("parquet").save("/hoangnlv/spark/output/big_device_model_num_user");
        device_model_list_user.repartition(1).write().mode("overwrite").format("orc").save("/hoangnlv/spark/output/big_device_model_list_user");
        action_by_button_id.repartition(1).write().mode("overwrite").format("parquet").save("/hoangnlv/spark/output/big_action_by_button_id");
    }
}
