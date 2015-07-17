package Messaging;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class NewTask {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws java.io.IOException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// Next we create a channel, which is where most of the API for getting
		// things done resides.
		// To send, we must declare a queue for us to send to; then we can
		// publish a message to the queue:
		// Declaring a queue is idempotent - it will only be created if it
		// doesn't exist already.
		// The message content is a byte array, so you can encode whatever you
		// like there.

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = getMessage(argv);
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 1)
			return "Hello World!";
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}
