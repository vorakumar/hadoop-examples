package examples.hadoop.mapreduce.join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;


public class MapReduceDriver {

    protected void runFirstJob(Path customers, Path orders, Path output, Configuration conf)
            throws Exception {
        Job job = Job.getInstance(conf, "join-customers-orders");
        job.setJarByClass(MapReduceDriver.class);

        job.setPartitionerClass(ClientOrderSortShuffle.CustomerPartitioner.class);
        job.setGroupingComparatorClass(ClientOrderSortShuffle.GroupComparator.class);

        job.setReducerClass(ClientOrderReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        MultipleInputs.addInputPath(job, customers, TextInputFormat.class, CustomerMapper.class);
        MultipleInputs.addInputPath(job, orders, TextInputFormat.class, OrderMapper.class);

        FileOutputFormat.setOutputPath(job, output);

        if (!job.waitForCompletion(true)) {
            throw new Exception("First Job (join-customers-orders) Failed");
        }

    }

    protected void runSecondJob(Path input, Path output, Configuration conf)
            throws Exception {
        Job job = Job.getInstance(conf, "spending by city job");
        job.setJarByClass(MapReduceDriver.class);
        job.setMapperClass(AmountByCityMapper.class);
        job.setCombinerClass(AmountByCityReducer.class);
        job.setReducerClass(AmountByCityReducer.class);
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);
        if (!job.waitForCompletion(true)) {
            throw new Exception("Second Job (spending by city job) Failed");
        }

    }

    public static void main(String[] args) throws Exception {

        MapReduceDriver mapReduceDriver = new MapReduceDriver();
        Path customers = new Path(args[0]);
        Path orders = new Path(args[1]);
        Path staging = new Path(args[2]);
        Path output = new Path(args[3]);

        Configuration conf = new Configuration();

        mapReduceDriver.runFirstJob(customers, orders, staging, conf);
        mapReduceDriver.runSecondJob(staging, output, conf);
    }


}

