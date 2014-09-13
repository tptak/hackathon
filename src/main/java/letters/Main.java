package letters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.List;

final class Main {

    public static void main(final String[] args) throws IOException, InterruptedException {
        final ConnectionFactory connectionFactory = connectionFactory();

        final WordsConsumer wordsConsumer = new WordsConsumer(
                new Consumer(connectionFactory, "words.found"),
                new ObjectMapper());

        final LetterPublisher publisher = new LetterPublisher(
                new Publisher(connectionFactory, "letter.created"));


        while (true) {
            final List<String> words = wordsConsumer.receiveNextWords();
            if (words != null) {
                if (words.isEmpty()) {
                    System.out.println("No words found");
                } else {
                    publisher.publishLetter('C');
                }
            }
        }
    }

    private static ConnectionFactory connectionFactory() {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("54.76.117.95");
        return factory;
    }
}
