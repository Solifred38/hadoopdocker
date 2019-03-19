package ooc.tp1;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordPerDocReduce extends Reducer<DocKey, WordCountWordPerDoc, DocKey, WordCountWordPerDoc>{
	@Override
	public void reduce(DocKey myDocKey, Iterable<WordCountWordPerDoc> values, Context context) throws IOException, InterruptedException {
			
			int sum=0;
			String word=new String();
			Text docname=myDocKey.getFilePathString();
			DocKey wordDocKey=new DocKey();
			
			
			
			for(WordCountWordPerDoc x: values)
			{
				sum+=x.getWordperdoc().get();
			}

			wordDocKey.set(word, docname.toString());
			// mise a jour du nombre de mots dans l'ensemble des documents
			for(WordCountWordPerDoc y: values)
			{
				y.setWordcount(new IntWritable(sum));
				context.write(wordDocKey, y);
			}
		
	}
	
}
