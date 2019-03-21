FROM sequenceiq/hadoop-docker
#FROM hadoop:fred
RUN yum update -y ; \
    yum upgrade -y ; \
    yum install nano -y ; \
    yum install mc -y ;
ADD start.sh /
ADD apache-maven-3.5.4-bin.tar.gz /
ENV PATH $PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin
ENV HADOOP_HOME /usr/local/hadoop

