package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AmountByCityMapper extends Mapper<Text, LongWritable, Text, LongWritable> {}
