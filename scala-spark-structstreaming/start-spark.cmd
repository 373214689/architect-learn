@echo off
SET WORKDIR=%~dp0
SET APP=%WORKDIR%/target/scala-spark-structstreaming-1.0-SNAPSHOT.jar
SET JARS=%WORKDIR%/lib/mysql-connector-java-8.0.26.jar

echo WORK_DIR=%WORKDIR%
echo SPARK_HOME=%SPARK_HOME%
echo SPARK_HOME=%SPARK_HOME%

%SPARK_HOME%/bin/spark-submit --master "local" --driver-memory 1g --driver-cores 1 --executor-memory 1g --executor-cores 1 --num-executors 1 --jars %JARS% --class com.liuyang.learn.App %APP%