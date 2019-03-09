package ooc.tp1;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
public class GoogleCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private String mapTaskId;
    private String inputFile;
    private int noRecords = 0;
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        // minuscule
        line = line.toLowerCase();
        StringTokenizer tokenizer = new StringTokenizer(line);
        //String filename = context.getConfiguration().get("map.input.file");
	String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
        while (tokenizer.hasMoreTokens()) {
	    String sToken=tokenizer.nextToken();
            sToken = sToken.replaceAll("[\\W]","");
     	    
            word.set("("+sToken+","+fileName+")");
            context.write(word, one);
        }
    }

    public void run(Context context) throws IOException, InterruptedException {
        setup(context);
        while (context.nextKeyValue()) {
            map(context.getCurrentKey(), context.getCurrentValue(), context);
        }
        cleanup(context);
    }

}


