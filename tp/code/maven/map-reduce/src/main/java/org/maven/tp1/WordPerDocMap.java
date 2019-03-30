package org.maven.tp1;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;



/*
le fichier d'entree est le resultat du mapreduce de la phase 1
value sera donc de la forme :
	mot  nomfichier nombreOccurence
*/

public class WordPerDocMap extends Mapper<LongWritable, Text, DocKey, WordCountWordPerDoc>{

	private DocKey myDockey = null;
	private WordCountWordPerDoc custom= null;
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	
		String line = value.toString();
						
		StringTokenizer tokenizer = new StringTokenizer(line);

		String word=tokenizer.nextToken();
		String docname=tokenizer.nextToken();
		int nb=Integer.parseInt(tokenizer.nextToken());
		
		myDockey= new DocKey(new Text(word),new Text(docname));
		custom = new WordCountWordPerDoc(new IntWritable(nb), new IntWritable(nb));
				
        context.write(myDockey, custom);
        
	}
}
