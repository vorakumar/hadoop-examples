package examples.hadoop.mapreduce.join;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyTest {

    @Test
    public void testCompareTo() throws Exception {
        Key one = new Key("1001","a");
        Key two = new Key("1001","b");

        assertEquals(-1,one.compareTo(two));
    }
}