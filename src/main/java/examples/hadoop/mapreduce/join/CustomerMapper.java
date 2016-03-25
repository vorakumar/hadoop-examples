package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class CustomerMapper extends Mapper<Object, Text, Key, Text> {
    public static final String SORT_TAG = "a";


    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

    }
}
