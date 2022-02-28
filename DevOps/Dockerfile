FROM openjdk:8

ADD ./target/knowing-server-0.0.1-SNAPSHOT.jar /opt/knowing/knowing-server/

EXPOSE 9000

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

CMD java -jar /opt/knowing/knowing-server/knowing-server-0.0.1-SNAPSHOT.jar
