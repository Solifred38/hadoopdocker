FROM sequenceiq/hadoop-docker
#FROM hadoop:fred
RUN yum update -y ; \
    yum upgrade -y ; \
    yum install nano -y ; \
    yum install mc -y ;
#   yum install -y java-1.8.0-openjdk-devel ; \
#   echo "JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")" | sudo tee -a /etc/profile ; 

ADD start.sh /
ADD apache-maven-3.5.4-bin.tar.gz /
ENV PATH $PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin:/apache-maven-3.5.4/bin
#ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.201.b09-2.el6_10.x86_64/jre/
ENV HADOOP_HOME /usr/local/hadoop

