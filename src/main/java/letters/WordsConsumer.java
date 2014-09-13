package letters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

final class WordsConsumer {

    private final ObjectMapper objectMapper;
    private final Consumer consumer;

    public WordsConsumer(final Consumer consumer,
                         final ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.consumer = consumer;
    }

    @SuppressWarnings("unchecked")
    List<String> receiveNextWords() throws IOException {
        return objectMapper
                .readValue(consumer.consumeNext(), List.class);

    }
}
