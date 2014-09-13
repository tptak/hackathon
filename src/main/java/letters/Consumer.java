package letters;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

final class Consumer {

    private final QueueingConsumer consumer;

    Consumer(final ConnectionFactory factory,
             final String topic) throws IOException {
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        final String queueName = channel.queueDeclare().getQueue();
        channel.exchangeDeclare("combo", "topic");
        channel.queueBind(queueName, "combo", topic);
        this.consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
    }

    String consumeNext() {
        try {
            return new String(consumer.nextDelivery().getBody());
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
