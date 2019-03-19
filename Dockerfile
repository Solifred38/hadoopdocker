FROM sequenceiq/hadoop-docker

RUN yum update -y ; \
    yum upgrade -y ; \
    yum install nano -y ; \
    yum install mc -y ;
ADD start.sh
ENV PATH $PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin
ENV HADOOP_HOME /usr/local/hadoop

