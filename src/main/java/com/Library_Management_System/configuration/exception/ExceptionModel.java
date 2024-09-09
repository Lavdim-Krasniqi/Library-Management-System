package com.Library_Management_System.configuration.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ExceptionModel {
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private List<String> messages;
    private int status;
    private String error;
    @JsonFormat(pattern = DATETIME_PATTERN)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time = LocalDateTime.now();

    public ExceptionModel() {
        super();
        this.messages = new ArrayList<>();
    }

    public ExceptionModel(String message, HttpStatus status) {
        super();
        addStatusFields(status);
        this.messages = Collections.singletonList(message);

    }

    public ExceptionModel(List<String> messages, HttpStatus status) {
        this.messages = messages;
        addStatusFields(status);
    }

    private void addStatusFields(HttpStatus status) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }
}
