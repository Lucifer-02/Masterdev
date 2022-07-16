package Main;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PeopleRecord implements Writable {

    public IntWritable id = new IntWritable();
    public Text firstName = new Text();
    public Text lastName = new Text();
    public IntWritable age = new IntWritable();
    public Text street = new Text();
    public Text city = new Text();
    public Text state = new Text();
    public IntWritable zip = new IntWritable();
    public Text job = new Text();


    public PeopleRecord() {
    }

    public PeopleRecord(int id, String firstName, String lastName, int age, String street, String city, String state, int zip) {
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.age.set(age);
        this.street.set(street);
        this.city.set(city);
        this.state.set(state);
        this.zip.set(zip);
    }

    public void write(DataOutput out) throws IOException {
        this.id.write(out);
        this.firstName.write(out);
        this.lastName.write(out);
        this.age.write(out);
        this.street.write(out);
        this.city.write(out);
        this.state.write(out);
        this.zip.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        this.id.readFields(in);
        this.firstName.readFields(in);
        this.lastName.readFields(in);
        this.age.readFields(in);
        this.street.readFields(in);
        this.city.readFields(in);
        this.state.readFields(in);
        this.zip.readFields(in);
    }
}
