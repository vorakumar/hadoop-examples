package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class AmountByCityMapper extends Mapper<Key, LongWritable, Key, LongWritable> { }
