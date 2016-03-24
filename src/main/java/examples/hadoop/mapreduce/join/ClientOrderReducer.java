package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;

import static java.lang.Long.parseLong;

public class ClientOrderReducer extends Reducer<Key, Text, Text, LongWritable> {
    Logger logger = Logger.getLogger(ClientOrderReducer.class);
    Text location = new Text("");

    @Override
    protected void reduce(Key key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        Iterator<Text> iterator = values.iterator();
        location = new Text(iterator.next());
        logger.info("Client Id: "+key.clientId+" Tag : "+key.tag+ "  Location: "+location.toString());
        while(iterator.hasNext()) {
            Text amount = iterator.next();
            context.write(location, new LongWritable(parseLong(amount.toString())));
        }
    }

}
