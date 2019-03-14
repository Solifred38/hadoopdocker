package ooc.tp1;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordPerDocReduce extends Reducer<Text, ValueWordPerDoc, DocKey, WordCountWordPerDoc>{

public void reduce(Text docname, Iterable<ValueWordPerDoc> values, Context context) throws IOException, InterruptedException {
		
		int sum=0;
		String word=new String();
		DocKey wordDocKey=new DocKey();
		IntWritable wordcount=new IntWritable(0);
		WordCountWordPerDoc wordCountWordPerDoc=new WordCountWordPerDoc();
		
		// Iteration sur les valeurs
		Iterator<ValueWordPerDoc> it = values.iterator();
		word=it.next().getWord().toString();
		
		while(it.hasNext()) {
			ValueWordPerDoc custom=it.next();
			wordcount=custom.getNb();
			word=custom.getWord().toString();
			
			sum+=(it.next().getNb()).get();
		}
		
		wordDocKey.set(word, docname.toString());
		wordCountWordPerDoc.set(wordcount, new IntWritable(sum));
		
		// Ecriture dans le contexte
		context.write(wordDocKey, wordCountWordPerDoc);
		
		
	}
	
}
