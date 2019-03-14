package ooc.tp1;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.hadoop.fs.Path;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.HashSet;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;

public class WordCountMap extends Mapper<LongWritable, Text, DocKey, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
//    private Text word = new Text();
    private String mapTaskId;
    private DocKey wordDoc = new DocKey();
    private Set<String> stopWords = new HashSet<String>();

    @SuppressWarnings("deprecation")
    public void setup(Context context) throws IOException {
        Path[] stopWordsFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        
        if (stopWordsFiles != null && stopWordsFiles.length > 0) {
            for (Path stopWordFile : stopWordsFiles) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(stopWordFile.toString()));
                String stopWord = null;
                while((stopWord = bufferedReader.readLine()) != null) {
                    stopWords.add(stopWord.toLowerCase());
                }
            }
        }
    }
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        // minuscule
        line = line.toLowerCase();
        StringTokenizer tokenizer = new StringTokenizer(line," .,;:-+*!?'\"/()[]{}<>|\t_&#0123456789");
        String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
        while (tokenizer.hasMoreTokens()) {
	        String sToken=tokenizer.nextToken();
            if (sToken.length()>2 && !stopWords.contains(sToken)) {
                wordDoc.set(sToken,fileName);
                context.write(wordDoc, one);
            }
        }
    }

}


