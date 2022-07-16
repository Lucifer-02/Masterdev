import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TokenizerMapper
		extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text num = new Text();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		if (!value.toString().isEmpty()) {
			num.set(value.toString());
			context.write(num, one);
		}
	}
}
