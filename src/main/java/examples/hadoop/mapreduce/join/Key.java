package examples.hadoop.mapreduce.join;

import com.google.common.collect.ComparisonChain;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Key implements WritableComparable<Key> {

    public Text clientId;
    public Text tag;

    public Key() {
        clientId = new Text();
        tag = new Text();
    }

    public Key(String clientId, String tag) {
        this.clientId = new Text(clientId);
        this.tag = new Text(tag);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        clientId.write(out);
        tag.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        clientId.readFields(in);
        tag.readFields(in);
    }

    @Override
    public int compareTo(Key o) {
        System.out.println(" "+o.clientId +" "+o.tag);
        return ComparisonChain.start()
                .compare(clientId, o.clientId)
                .compare(tag, o.tag)
                .result();
    }
}
