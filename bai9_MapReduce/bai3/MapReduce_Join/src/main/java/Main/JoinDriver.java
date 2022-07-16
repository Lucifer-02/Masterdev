package Main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class JoinDriver {
    public static class JoinGroupingComparator extends WritableComparator {
        public JoinGroupingComparator() {
            super(JobKey.class, true);
        }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            JobKey first = (JobKey) a;
            JobKey second = (JobKey) b;

            return first.job.compareTo(second.job);
        }
    }

    public static class JoinSortingComparator extends WritableComparator {
        public JoinSortingComparator() {
            super(JobKey.class, true);
        }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            JobKey first = (JobKey) a;
            JobKey second = (JobKey) b;

            return first.compareTo(second);
        }
    }

    public static class SalaryMapper extends Mapper<LongWritable,
            Text, JobKey, JoinGenericWritable> {
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] recordFields = value.toString().split(",");
            String job = recordFields[0];
            try {
                int salary = Integer.parseInt(recordFields[1]);
                JobKey recordKey = new JobKey(job, JobKey.SALARY_RECORD);
                SalaryRecord record = new SalaryRecord(salary);

                JoinGenericWritable genericRecord = new JoinGenericWritable(record);
                context.write(recordKey, genericRecord);
            } catch (NumberFormatException e) {
                System.out.println("Skip header");
            }
        }
    }

    public static class PeopleMapper extends Mapper<LongWritable,
            Text, JobKey, JoinGenericWritable> {
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] recordFields = value.toString().split(",");
            try {
                String job = recordFields[8];
                int id = Integer.parseInt(recordFields[0]);
                String firstName = recordFields[1];
                String lastName = recordFields[2];
                int age = Integer.parseInt(recordFields[3]);
                String street = recordFields[4];
                String city = recordFields[5];
                String state = recordFields[6];
                int zip = Integer.parseInt(recordFields[7]);

                JobKey recordKey = new JobKey(job, JobKey.PEOPLE_RECORD);
                PeopleRecord record = new PeopleRecord(id, firstName, lastName, age, street, city, state, zip);
                JoinGenericWritable genericRecord = new JoinGenericWritable(record);
                context.write(recordKey, genericRecord);
            } catch (NumberFormatException e) {
                System.out.println("Skip header");
            }
        }
    }

    public static class JoinReducer extends Reducer<JobKey,
            JoinGenericWritable, NullWritable, Text> {

        StringBuilder output = new StringBuilder();

        @Override
        protected void setup(Reducer<JobKey, JoinGenericWritable, NullWritable, Text>.Context context) throws IOException, InterruptedException {
            output.append("id,job,first_name,last_name,age,street,city,state,zip,salary\n");
        }

        public void reduce(JobKey key, Iterable<JoinGenericWritable> values,
                           Context context) throws IOException, InterruptedException {
            String salary = "";
            for (JoinGenericWritable v : values) {
                Writable record = v.get();
                if (key.recordType.equals(JobKey.SALARY_RECORD)) {
                    SalaryRecord record2 = (SalaryRecord) record;
                    salary = record2.salary.toString();
                } else {
                    PeopleRecord pRecord = (PeopleRecord) record;
                    output.append(pRecord.id).append(", ");
                    output.append(key.job).append(", ");
                    output.append(pRecord.firstName).append(",");
                    output.append(pRecord.lastName).append(",");
                    output.append(pRecord.age).append(",");
                    output.append(pRecord.street).append(",");
                    output.append(pRecord.city).append(",");
                    output.append(pRecord.state).append(",");
                    output.append(pRecord.zip).append(",");
                    output.append(salary).append("\n");
                }
            }
        }

        @Override
        protected void cleanup(Reducer<JobKey, JoinGenericWritable, NullWritable, Text>.Context context) throws IOException, InterruptedException {
            context.write(NullWritable.get(), new Text(output.toString()));
        }

    }

    public static void main(String[] allArgs) throws Exception {
        Configuration conf = new Configuration();
        String[] args = new GenericOptionsParser(conf, allArgs).getRemainingArgs();

        Job job = Job.getInstance(conf);
        job.setJarByClass(JoinDriver.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapOutputKeyClass(JobKey.class);
        job.setMapOutputValueClass(JoinGenericWritable.class);

        MultipleInputs.addInputPath(job, new Path(args[0]),
                TextInputFormat.class, PeopleMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]),
                TextInputFormat.class, SalaryMapper.class);

        job.setReducerClass(JoinReducer.class);

        job.setSortComparatorClass(JoinSortingComparator.class);
        job.setGroupingComparatorClass(JoinGroupingComparator.class);
        job.setNumReduceTasks(3);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        FileOutputFormat.setOutputPath(job, new Path(allArgs[2]));
        job.waitForCompletion(true);
    }
}
