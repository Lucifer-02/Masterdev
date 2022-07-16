package Main;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class JobKey implements WritableComparable<JobKey> {
    public Text job = new Text();
    public IntWritable recordType = new IntWritable();

    public JobKey() {
    }

    public JobKey(String job, IntWritable recordType) {
        this.job.set(job);
        this.recordType = recordType;
    }

    @Override
    public int compareTo(JobKey other) {
        if (this.job.equals(other.job)) {
            return this.recordType.compareTo(other.recordType);
        } else {
            return this.job.compareTo(other.job);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.job.write(out);
        this.recordType.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.job.readFields(in);
        this.recordType.readFields(in);
    }

    public boolean equals(JobKey other) {
        return this.job.equals(other.job) && this.recordType.equals(other.recordType);
    }

    public int hashCode() {
        return this.job.hashCode();
    }

    public static final IntWritable PEOPLE_RECORD = new IntWritable(1);
    public static final IntWritable SALARY_RECORD = new IntWritable(0);
}
