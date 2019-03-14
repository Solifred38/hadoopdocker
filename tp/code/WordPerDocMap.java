package ooc.tp1;

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

public class WordPerDocMap extends Mapper<LongWritable, Text, Text, ValueWordPerDoc>{

	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	
		String line = value.toString();
						
		StringTokenizer tokenizer = new StringTokenizer(line);

		String word=tokenizer.nextToken();
		String docname=tokenizer.nextToken();
		int nb=Integer.parseInt(tokenizer.nextToken());
		
		ValueWordPerDoc custom=new ValueWordPerDoc(new Text(word), new IntWritable(nb));
				
        context.write(new Text(docname), custom);
        
	}
}
