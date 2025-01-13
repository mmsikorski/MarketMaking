package org.quickfixj.domain.kraken;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.ARRAY)

public class TickerModel {
    @JsonProperty("id")
    private long id;
    @JsonProperty("details")
    private TickerDetails details;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("pair")
    private String pair;

    // Getters and Setters
    // toString method
}

