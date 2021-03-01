package zephyr;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class AvroProducerTest {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "cluster-01-kafka-external-bootstrap.kafka:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "https://schema-registry.fusionskye.work");

        Producer<String, GenericRecord> producer = new KafkaProducer<>(properties);

        //avro schema
        String simpleMessageSchema =
                "{" +
                        " \"type\": \"record\"," +
                        " \"name\": \"SimpleMessage\"," +
                        " \"namespace\": \"com.fusionskye.zephyrtest\"," +
                        " \"fields\": [" +
                        " {\"name\": \"content\", \"type\": \"string\", \"doc\": \"Message content\"}," +
                        " {\"name\": \"date_time\", \"type\": \"long\", \"logicalType\": \"timestamp\", \"doc\": \"Datetime when the message\"}" +
                        " ]" +
                        "}";

        //parse the schema
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(simpleMessageSchema);

        //prepare the avro record
        GenericRecord record1 = new GenericData.Record(schema);
        record1.put("content", "Hello world");
        record1.put("date_time", System.currentTimeMillis());
        GenericRecord record2 = new GenericData.Record(schema);
        record2.put("content", "Hello world");
        record2.put("date_time", System.currentTimeMillis());

        //prepare the kafka record
        ProducerRecord<String, GenericRecord> producerRecord1 = new ProducerRecord<>("avro-topic", null, record1);
        ProducerRecord<String, GenericRecord> producerRecord2 = new ProducerRecord<>("avro-topic", null, record2);

        producer.send(producerRecord1);
        producer.send(producerRecord2);
        //ensures record is sent before closing the producer
        producer.flush();

        producer.close();
    }

}
