package examples.hadoop.mapreduce.join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;


public class MapReduceDriver {

    protected void runFirstJob(Path customers, Path orders, Path output, Configuration conf)
            throws Exception {
        Job job = Job.getInstance(conf, "join-customers-orders");
        job.setJarByClass(MapReduceDriver.class);

        job.setSortComparatorClass(ClientOrderSortShuffle.SortComparator.class);
        job.setPartitionerClass(ClientOrderSortShuffle.CustomerPartitioner.class);
        job.setGroupingComparatorClass(ClientOrderSortShuffle.GroupComparator.class);

        job.setReducerClass(ClientOrderReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setMapOutputKeyClass(Key.class);
        job.setMapOutputValueClass(Text.class);
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

        job.setInputFormatClass(SequenceFileInputFormat.class);
        job.setJarByClass(MapReduceDriver.class);
        job.setMapperClass(AmountByCityMapper.class);
        job.setCombinerClass(AmountByCityReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);
        if (!job.waitForCompletion(true)) {
            throw new Exception("Second Job (spending by city job) Failed");
        }

    }

    public static void main(String[] args) throws Exception {

        MapReduceDriver mapReduceDriver = new MapReduceDriver();
        String dataDir = args[0];
        Path customers = new Path(dataDir+"/customers.csv");
        Path orders = new Path(dataDir+"/orders.csv");
        Path staging = new Path(dataDir+"/staging");
        Path output = new Path(dataDir+"/output");

        Configuration conf = new Configuration();

        mapReduceDriver.runFirstJob(customers, orders, staging, conf);
        mapReduceDriver.runSecondJob(staging, output, conf);
    }


}

