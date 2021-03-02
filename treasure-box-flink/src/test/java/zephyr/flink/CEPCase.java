/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zephyr.flink;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.functions.TimedOutPartialMatchHandler;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.datagen.DataGenerator;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.test.util.AbstractTestBase;
import org.apache.flink.util.Collector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * cep test cases
 */
public class CEPCase extends AbstractTestBase implements Serializable {
    @Rule
    public transient TemporaryFolder tempFolder = new TemporaryFolder();


    @Test
    public void testEventTimeWithWait() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
//		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        SingleOutputStreamOperator<Event> input = env
                .addSource(new DataGeneratorSource<>(new EventGroupDataGenerator()))
                .returns(TypeInformation.of(new TypeHint<Event>() {
                }));
        KeyedStream<Event, String> keyedStream = input
//			.assignTimestampsAndWatermarks(
//				WatermarkStrategy
//					.<Event>forBoundedOutOfOrderness(Duration.ofMillis(5L))
//					.withTimestampAssigner((event, timestamp) -> event)
//			)
                .keyBy(new KeySelector<Event, String>() {
                    @Override
                    public String getKey(Event value) throws Exception {
                        return value.getName();
                    }
                });

        input.print();

        PatternStream<Event> output = CEP.pattern(
                keyedStream,
                Pattern.<Event>begin("p0").where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        return value.getId() == 0;
                    }
                })
//				.wait("w1").waitting(Time.milliseconds(500L))
                        .followedBy("p1").where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        return value.getId() == 1;
                    }
                })
//				.wait("w2").waitting(Time.milliseconds(500L))
                        .followedBy("p2").where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        return value.getId() == 2;
                    }
                })
//				.wait("w3").waitting(Time.milliseconds(500L))
                        .followedBy("p3").where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        return value.getId() == 3;
                    }
                })
                        .within(Time.seconds(2))
        );

        output.process(new MyPatternProcessFunction()).print();

        env.execute();
    }

    // 数据生成器
    private static class EventGroupDataGenerator implements DataGenerator<Event> {
        final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        final AtomicInteger atomicInteger = new AtomicInteger();
        final Map<Integer, Long> map = new ConcurrentHashMap<>();

        public EventGroupDataGenerator() {
            super();
            map.put(0, 1000L);
            map.put(1, 1000L);
            map.put(2, 1000L);
            map.put(3, 1000L);
            map.put(4, 1000L);
            map.put(5, 1000L);
            map.put(6, 1000L);
            map.put(7, 1000L);
        }

        @Override
        public boolean hasNext() {
            return atomicInteger.get() != map.size();
        }

        @Override
        public Event next() {
            int id = atomicInteger.getAndIncrement();
            try {
                Thread.sleep(map.get(id));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Event.of(id, "test", randomDataGenerator.nextLong(0L, Long.MAX_VALUE), System.currentTimeMillis());
        }

        @Override
        public void open(String name, FunctionInitializationContext context, RuntimeContext runtimeContext) throws Exception {
            System.out.println("====> DataGenerator Open!");
        }
    }

    private static class MyPatternProcessFunction extends PatternProcessFunction<Event, Event> implements TimedOutPartialMatchHandler<Event> {

        @Override
        public void processMatch(Map<String, List<Event>> match, Context ctx, Collector<Event> out) throws Exception {
            System.out.println(match);
        }

        @Override
        public void processTimedOutMatch(Map<String, List<Event>> match, Context ctx) throws Exception {
            System.out.println("timeout" + match);
//            ctx.output(null, new T(startEvent));
        }
    }
}
