package vn.molu.common.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeModule extends SimpleModule {
    public LocalDateTimeModule() {
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    }

    private static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        private final DateTimeFormatter formatter2= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String dateText = parser.getText();
            try{
                return LocalDateTime.parse(dateText, formatter1);
            }catch (Exception ignored){
                return LocalDateTime.parse(dateText,formatter2);
            }
        }
    }
}
