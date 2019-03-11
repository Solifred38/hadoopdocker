#stop du dernier conteneur
docker stop hadoop
docker run --rm -P -d  -v $PWD/tp:/tp --name hadoop hadoop:fred /etc/bootstrap.sh -d
