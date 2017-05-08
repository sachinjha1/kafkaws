package sample;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MyConsumer {

	public static void main(String[] args) {
		 Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
		 //props.put("acks", "all");
		 //props.put("retries", 0);
		 //props.put("batch.size", 16384);//Records Accumulator - maximum number of bytes that can be buffered per each record batch so that whole recored batch can be sent at once.
		 //props.put("linger.ms", 1);// how long it will wait to see if it can full a record batch. if not full just send it after this ms time.
		 //props.put("buffer.memory", 33554432);//max no of buffered records waiting to be sent to broker. max.block.ms is time in ms to block send call when buff memory is full so that pending records can be flushed
		 props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		 props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		 //above props is used to instantiate producerConfig internally by KafkaProducer
		 //abobe key value serializer is tightly linked to what datatype you are sending in ProcucerRecord which is also string here
		 
		 Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		 consumer.subscribe(Arrays.asList("my-topic"));
		 
		 try {			
			
			while(true){
				ConsumerRecords<String, String> records = consumer.poll(100);
				for(ConsumerRecord<String, String> rec: records){
					System.out.println(rec.topic() +"$"+ rec.partition() +"$" + rec.offset() + "$"+ rec.key() +"$" +rec.value());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			consumer.unsubscribe();
			consumer.close();
		}

	}

}
