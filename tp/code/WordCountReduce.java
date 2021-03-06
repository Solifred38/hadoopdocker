package ooc.tp1;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReduce extends Reducer<DocKey, IntWritable, DocKey, IntWritable> {

    private IntWritable totalWordCount = new IntWritable();

    @Override
    public void reduce(final DocKey key, final Iterable<IntWritable> values,
            final Context context) throws IOException, InterruptedException {

        int sum = 0;
        Iterator<IntWritable> iterator = values.iterator();

        while (iterator.hasNext()) {
            sum += iterator.next().get();
        }

        totalWordCount.set(sum);
        context.write(key, totalWordCount);
    }

}

