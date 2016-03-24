package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AmountByCityMapper extends Mapper<Text, LongWritable, Text, LongWritable> {
    Logger logger = Logger.getLogger(AmountByCityMapper.class);

    @Override
    protected void map(Text key, LongWritable value, Context context) throws IOException, InterruptedException {
        logger.info(key);
        context.write(key,  value);
    }
}
