#stop du dernier conteneur
docker stop hadoop
docker run --rm -P -d  -v $PWD/tp:/tp -v $PWD/logs:/usr/local/hadoop-2.7.0/logs/userlogs --name hadoop hadoop:fred /etc/bootstrap.sh -d
chown -R fromager:fromager $PWD/logs
sleep 5
docker exec -t hadoop hadoop dfsadmin -safemode leave
docker exec -t hadoop ./start.sh
