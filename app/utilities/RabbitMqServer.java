package utilities;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by satheesh on 15/9/14.
 */
public class RabbitMqServer {

    public static Channel getChannelInstance() throws IOException {
        final String QUEUE_NAME = "twitterdata";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        return channel;

    }

}
