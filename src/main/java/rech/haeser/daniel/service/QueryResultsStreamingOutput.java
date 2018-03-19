package rech.haeser.daniel.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.mongodb.morphia.query.MorphiaIterator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author daniel.rech
 */
class QueryResultsStreamingOutput<T, R> implements StreamingOutput {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new JsonFactory().disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET));

    private final MorphiaIterator<T, R> iterator;

    QueryResultsStreamingOutput(final MorphiaIterator<T, R> i) {
        iterator = i;
    }

    @Override
    public void write(final OutputStream outputStream) throws IOException, WebApplicationException {

        final OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8.name());

        if (iterator.hasNext()) {
            writer.write('[');
            while (true) {
                final R result = iterator.next();
                OBJECT_MAPPER.writeValue(writer, result);
                if (iterator.hasNext()) {
                    writer.write(',');
                } else {
                    break;
                }
            }
            writer.write(']');
            writer.flush();
        } else {
            throw new NotFoundException();
        }
    }
}
