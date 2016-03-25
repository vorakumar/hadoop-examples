package examples.hadoop.mapreduce.join;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.log4j.Logger;

public class ClientOrderSortShuffle {

    public static class CustomerPartitioner extends Partitioner<Key, Text> {
        @Override
        public int getPartition(Key key, Text text, int numPartitions) {
            return -1;
        }
    }

    public static class GroupComparator extends WritableComparator {
        public GroupComparator() { super(Key.class, true); }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            return -1;
        }
    }

    public static class SortComparator extends WritableComparator {
        public SortComparator() { super(Key.class, true); }
    }
}
