package letters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

final class Main {

    public static void main(final String[] args) throws IOException, InterruptedException {
        LetterGenerator producer = new LetterGenerator(Main.class.getResourceAsStream("/horse.json"));

        final ConnectionFactory connectionFactory = connectionFactory();

        final WordsConsumer wordsConsumer = new WordsConsumer(
                new Consumer(connectionFactory, "words_found"),
                new ObjectMapper());

        final LetterPublisher publisher = new LetterPublisher(
                new Publisher(connectionFactory, "letter_created"));

        while (true) {
            final Map<String, Object> message = wordsConsumer.receiveNextWords();
            final Integer gameId = (Integer) message.get("id");
            final Collection<Object> words = (Collection<Object>) message.get("words");
            if (words != null) {
                if (words.isEmpty()) {
                    publisher.publishLetterForGame(gameId, producer.nextLetter());
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
