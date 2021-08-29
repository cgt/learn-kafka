import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.Properties;

public class Producer {
    public static void main(String[] args) throws InterruptedException {
        final var config = new Properties();
        config.put("bootstrap.servers", "localhost:29092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.VoidSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        final var producer = new KafkaProducer<Void, String>(config);
        final var workers = new ArrayList<Thread>();
        for (var worker = 0; worker < 4; worker++) {
            final var workerId = worker;
            final var t = new Thread(() -> {
                for (var i = 0; i < Integer.MAX_VALUE; i++) {
                    final var value = String.format("Hello from worker %d, %d", workerId, i);
                    final var message = new ProducerRecord<Void, String>("foo", value);
                    producer.send(message);
                }
            });
            t.start();
            workers.add(t);
        }
        for (final var worker : workers) {
            worker.join();
        }
    }
}
