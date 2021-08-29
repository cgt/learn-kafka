import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Consumer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final var config = new Properties();
        config.put("bootstrap.servers", "localhost:29092");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.VoidDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id", "MyConsumer");
        final var consumer = new KafkaConsumer<Void, String>(config);
        consumer.subscribe(List.of("foo"));
        while (true) {
            final var records = consumer.poll(Duration.ofSeconds(1));
            for (final var record : records) {
                System.out.printf(
                  "topic = %s, partition = %d, offset = %d, key = %s, value = %s\n",
                  record.topic(),
                  record.partition(),
                  record.offset(),
                  record.key(),
                  record.value()
                );
            }
        }
    }
}
