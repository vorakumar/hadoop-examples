package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class CustomerMapper extends Mapper<Object, Text, Key, Text> {
    public static final String SORT_TAG = "a";

    private Text location = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split(",");
        location.set(tokens[2]);
        context.write(new Key(tokens[0], SORT_TAG), location);
    }
}
