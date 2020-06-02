package com.flax.finplat.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flax.finplat.common.Calculator;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }

    /**
     * Customize json converter
     * - pretty output
     * - fail on unknown errors
     * - calculator normalization of BigDecimal
     *
     * @param converters converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        Optional<HttpMessageConverter<?>> messageConverter = converters.stream().filter(
                c -> c instanceof MappingJackson2HttpMessageConverter).findFirst();
        messageConverter.ifPresent(httpMessageConverter -> {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) httpMessageConverter;
            converter.getObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .registerModule(new SimpleModule()
                                            .addSerializer(BigDecimal.class, new MoneySerializer())
                                            .addDeserializer(BigDecimal.class, new MoneyDeserializer())
                                            .addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer()))
                    .registerModule(new JavaTimeModule());
        });
    }

    private static class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
        @Override
        public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(
                            offsetDateTime));
        }
    }

    private static class MoneySerializer extends JsonSerializer<BigDecimal> {
        @Override
        public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(Calculator.normalize(value).toPlainString());
        }
    }

    private static class MoneyDeserializer extends JsonDeserializer<BigDecimal> {

        @Override
        public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return Calculator.normalize(p.getValueAsString());
        }
    }
}
