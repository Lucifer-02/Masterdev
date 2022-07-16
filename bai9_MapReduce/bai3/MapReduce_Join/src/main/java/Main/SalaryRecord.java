package Main;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SalaryRecord implements Writable {
    public IntWritable salary = new IntWritable();

    public SalaryRecord() {
    }

    public SalaryRecord(int salary) {
        this.salary.set(salary);
    }

    public void write(DataOutput out) throws IOException {
        this.salary.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        this.salary.readFields(in);
    }
}
