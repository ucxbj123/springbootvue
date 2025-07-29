FROM jdk:1.8
MAINTAINER xiebj1<xiebj1@qq.com>

ENV MYPATH /root/
WORKDIR $MYPATH

#ADD 是相对路径jar，把jar添加到容器中，包必须要和Dockerfile文件在同一位置
ADD project/springbootvue*.jar /root/project/app.jar

RUN echo $MYPATH
RUN echo "success------------ok"

ENTRYPOINT ["java","-jar","/root/project/app.jar"]
