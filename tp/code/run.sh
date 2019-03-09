#/bin/bash
javac -classpath $(hadoop classpath) *.java
mv *.class ooc/tp1/
jar -cvf ooc_tp1.jar -C . ooc 
hdfs dfs -put ../data data
hadoop jar ooc_tp1.jar ooc.tp1.GoogleCountDriver data results
rm -rf results
hdfs dfs -get results
