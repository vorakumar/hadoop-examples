package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AmountByCityReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
    private LongWritable totalAmountForCity = new LongWritable();

    @Override
    protected void reduce(Text city, Iterable<LongWritable> orderTotals, Context context) throws IOException, InterruptedException {
        long total = 0;
        for (LongWritable value : orderTotals) {
            total += value.get();
        }
        totalAmountForCity.set(total);
        context.write(city, totalAmountForCity);
    }
}
