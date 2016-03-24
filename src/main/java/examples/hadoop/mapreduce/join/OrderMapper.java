package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class OrderMapper extends Mapper<Object, Text, Key, Text> {
    public static final String SORT_TAG = "b";
    private Text amount = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split(",");
        amount.set(tokens[2]);
        context.write(new Key(tokens[1], SORT_TAG), amount);
    }
}
