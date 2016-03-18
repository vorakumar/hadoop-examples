package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Partitioner;

public class ClientOrderSortShuffle {

    public static class CustomerPartitioner extends Partitioner<Key, Text> {
        @Override
        public int getPartition(Key key, Text text, int numPartitions) {
            return key.clientId.hashCode() / numPartitions;
        }
    }

    public static class GroupComparator extends WritableComparator {
        public GroupComparator() {
            super(Key.class, true);
        }

        @Override
        public int compare(Object a, Object b) {
            return ((Key) a).clientId.compareTo(((Key) b).clientId);
        }
    }
}
