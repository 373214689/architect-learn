package flink.base.window;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;

import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.Random;

public class LearnTumblingWindow {


    public static void main(String[] args) {

        testDataStreamSource();
    }

    private static void testDataStreamSource() {
        // 自定义数据源
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<String> dataStreamSource = env.addSource(new SourceFunction<String>() {
            private boolean running = true;
            @Override
            public void run(SourceContext<String> ctx) throws Exception {
                Random random = new Random();
                // 循环可以不停的读取静态数据
                while (running) {
                    int nextInt = random.nextInt(100);
                    ctx.collect("random : " + nextInt);
                    Thread.sleep(1000);
                }
            }
            @Override
            public void cancel() {
                running = false;
            }
        });

        WindowedStream<Tuple2<String, Integer>, Tuple, TimeWindow> window = dataStreamSource.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                String[] sps = value.split(":");
                return new Tuple2<>(value, Integer.parseInt(sps[1].trim()));
            }
        }).keyBy(0).timeWindow(Time.seconds(5));

        SingleOutputStreamOperator<String> apply = window.apply(new WindowFunction<Tuple2<String, Integer>, String, Tuple, TimeWindow>() {
            @Override
            public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<String, Integer>> input, Collector<String> out) throws Exception {
                input.forEach(x -> {
                    System.out.println("apply function -> " + x.f0);
                    out.collect(x.f0);
                });
            }
        });

        apply.print();

        try {
            env.execute("myself_source_test01");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testSocketStream() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.socketTextStream("localhost", 9000)
                .flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {

                    public void flatMap(String value, Collector<Tuple2<String, Long>> out) throws Exception {
                        Arrays.stream(value.split("\\W+"))
                                .forEach(word -> out.collect(Tuple2.of(word, 1L)));

                    }
                })
                .keyBy(t -> t.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(8)))
                .sum(1)
                .print();
    }
}
