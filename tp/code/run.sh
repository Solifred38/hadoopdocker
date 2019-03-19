#/bin/bash
javac -classpath $(hadoop classpath) *.java
rm -rf ooc/tp1
mkdir -p ooc/tp1
mv *.class ooc/tp1/
jar -cvf ooc_tp1.jar -C . ooc 
hdfs dfs -put ../data data
hdfs dfs -put ../cache cache
hadoop jar ooc_tp1.jar ooc.tp1.WordCountDriver data results results2
rm -rf results*
hdfs dfs -get results
hdfs dfs -get results2
