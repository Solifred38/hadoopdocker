#stop du dernier conteneur
#docker stop hadoop
docker rm -f hadoop
docker run --rm -P -p 80:80 -d  -v /home/fromager/Dev/maven:/maven -v $PWD/tp:/tp -v $PWD/logs:/usr/local/hadoop-2.7.0/logs/userlogs --name hadoop hadoop:fred /etc/bootstrap.sh -d
sleep 5
docker exec -t hadoop hdfs dfsadmin -safemode leave
docker exec -t hadoop ./start.sh
