package com.liuyang.learn

import org.apache.flink.configuration.{ConfigConstants, Configuration}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
 * Hello world!
 *
 */
object FlinkApp {
  def main(args: Array[String]): Unit = {
    println( "Hello World!" )

    //生成了配置对象
    val config = new Configuration()
    //打开flink-webui
    config.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true)
    //配置webui的日志文件，否则打印日志到控制台，这样你的控制台就清净了
    config.setString("web.log.path", "logs/flink.log")
    //配置taskManager的日志文件，否则打印日志到控制台，这样你的控制台就清净了
    config.setString(ConfigConstants.TASK_MANAGER_LOG_PATH_KEY, "logs/flink.log")

    //获得local运行环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config)
    //定义socket的source源
    val text: DataStream[String] = env.socketTextStream(hostname="localhost", port = 6666)

    //scala开发需要加一行隐式转换，否则在调用operator的时候会报错，作用是找到scala类型的TypeInformation
    import org.apache.flink.api.scala._

    //定义operators，作用是解析数据，分组，并且求wordCount
    val wordCount: DataStream[(String, Int)] = text.flatMap(_.split(" ")).map((_,1)).keyBy(_._1).sum( position = 1)

    //定义sink，打印数据到控制台
    wordCount.print()

    //定义任务的名称并运行
    //注意：operator是惰性的，只有遇到execute才执行
    env.execute(jobName = "SocketWordCount")
  }

}
