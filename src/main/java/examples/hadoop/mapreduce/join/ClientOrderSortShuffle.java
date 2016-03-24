package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.log4j.Logger;

public class ClientOrderSortShuffle {

    public static class CustomerPartitioner extends Partitioner<Key, Text> {
        @Override
        public int getPartition(Key key, Text text, int numPartitions) {
            return (key.clientId.hashCode() & Integer.MAX_VALUE) / numPartitions;
        }
    }

    private static abstract class KeyRawComparator implements RawComparator<Key> {
        DataInputBuffer buffer = new DataInputBuffer();
        Key a = new Key();
        Key b = new Key();

        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2 ) {
            try {
                buffer.reset(b1, s1, l1);
                a.readFields(buffer);
                buffer.reset(b2, s2, l2);
                b.readFields(buffer);
                return compare(a,b);
            } catch(Exception ex) {
                return -1;
            }
        }
    }

    public static class GroupComparator extends KeyRawComparator {
        @Override
        public int compare(Key o1, Key o2) {
            return o1.clientId.compareTo(o2.clientId);
        }
    }

    public static class SortComparator extends KeyRawComparator {
        private Logger logger = Logger.getLogger(SortComparator.class);


        @Override
        public int compare(Key o1, Key o2) {
            logger.info("  "+o1.clientId+","+o1.tag+","+o2.clientId+","+ o2.tag);
            return o1.compareTo(o2);
        }
    }
}
