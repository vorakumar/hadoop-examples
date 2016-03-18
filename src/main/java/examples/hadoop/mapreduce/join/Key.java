package examples.hadoop.mapreduce.join;

import com.google.common.collect.ComparisonChain;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
public class Key implements WritableComparable<Key> {

    public String clientId;
    public String tag;

    public Key(String clientId, String tag) {
        this.clientId = clientId;
        this.tag = tag;
    }

    @Override
    public void write(DataOutput out) throws IOException { }

    @Override
    public void readFields(DataInput in) throws IOException { }

    @Override
    public int compareTo(Key o) {
        return ComparisonChain.start()
                .compare(clientId, o.clientId)
                .compare(tag, o.tag)
                .result();
    }
}
