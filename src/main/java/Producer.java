import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final var config = new Properties();
        config.put("bootstrap.servers", "localhost:29092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.VoidSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        final var producer = new KafkaProducer<Void, String>(config);
        final var message = new ProducerRecord<Void, String>("foo", "Hello, World!");
        final var response = producer.send(message).get();
        System.out.println(response);
    }
}
