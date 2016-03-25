package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;

import static java.lang.Long.parseLong;

public class ClientOrderReducer extends Reducer<Key, Text, Text, LongWritable> {

    @Override
    protected void reduce(Key key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Iterator<Text> iterator = values.iterator();
        Text location = new Text(iterator.next());
        while(iterator.hasNext()) {
            Text amount = iterator.next();
            context.write(location, new LongWritable(parseLong(amount.toString())));
        }
    }

}
