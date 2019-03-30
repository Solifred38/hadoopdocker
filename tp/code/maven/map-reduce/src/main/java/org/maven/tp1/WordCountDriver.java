package org.maven.tp1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
/*
Algorithme en 2 phases
phase 1 : 
    - comptage des mots sur l'ensemble des documents

On filtre avec les stopwords et on sort le fichier de resultat qui sera utilise en entree de la phase 2

phase 2 : 
    - comptage des mots dans chaque document

*/
public class WordCountDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage: [input] [output1] [output2]");
            System.exit(-1);
        }
        System.out.println("start process ");
        // Creation d'un job en lui fournissant la configuration et une description textuelle de la tache
        Configuration config = new Configuration();
       //Job job = Job.getInstance(getConf());
        Job job = Job.getInstance(config);
       job.setJobName("WordCountJob1");

        // Ajout des stopwords dans le cache du job
        job.addCacheFile(new Path("cache/stopwords_en.txt").toUri());
        // On precise les classes MyProgram, Map et Reduce

        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMap.class);
        job.setReducerClass(WordCountReduce.class);

 // Definition des types cle/valeur de notre probleme
        job.setOutputKeyClass(DocKey.class);
        job.setOutputValueClass(IntWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        Path inputFilePath = new Path(args[0]);
        Path outputFilePath = new Path(args[1]);

	// trace des fichiers entres
	System.out.println("inputFilePath : "+inputFilePath);
// On accepte une entree recursive
       FileInputFormat.setInputDirRecursive(job, true);

        FileInputFormat.addInputPath(job, inputFilePath);
        FileOutputFormat.setOutputPath(job, outputFilePath);

        //FileSystem fs = FileSystem.newInstance(getConf());
        FileSystem fs = FileSystem.newInstance(config);

        if (fs.exists(outputFilePath)) {
            fs.delete(outputFilePath, true);
        }

//        return job.waitForCompletion(true) ? 0: 1;
        job.waitForCompletion(true);

// deuxieme partie du job
         // Creation d'un job en lui fournissant la configuration et une description textuelle de la tache

       //Job job2 = Job.getInstance(getConf());
        Job job2=Job.getInstance(config);
       job2.setJobName("wordcountperdoc");

        // outputFilePath sera aussi le inputFilePath du traitement suivant
        //Path inputFilePath2 = new Path(outputFilePath.toString());
       Path inputFilePath2 = outputFilePath;
        Path outputFilePath2= new Path(args[2]);
        job2.setJarByClass(WordCountDriver.class);
        job2.setMapperClass(WordPerDocMap.class);
       job2.setReducerClass(WordPerDocReduce.class);

 // Definition des types cle/valeur de notre probleme
        job2.setOutputKeyClass(DocKey.class);
//        job2.setOutputValueClass(ValueWordPerDoc.class);
        job2.setOutputValueClass(WordCountWordPerDoc.class);

        job2.setInputFormatClass(TextInputFormat.class);
        job2.setOutputFormatClass(TextOutputFormat.class);

    // trace des fichiers entres
    System.out.println("inputFilePath2 : "+inputFilePath2);
// On accepte une entree recursive
       FileInputFormat.setInputDirRecursive(job2, true);

        FileInputFormat.addInputPath(job2, inputFilePath2);
        FileOutputFormat.setOutputPath(job2, outputFilePath2);

        //FileSystem fs2 = FileSystem.newInstance(getConf());
        FileSystem fs2 = FileSystem.newInstance(config);

        if (fs2.exists(outputFilePath2)) {
            fs2.delete(outputFilePath2, true);
        }
        job2.waitForCompletion(true);
    }

}
