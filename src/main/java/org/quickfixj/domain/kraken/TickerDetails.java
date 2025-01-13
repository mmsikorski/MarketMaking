package org.quickfixj.domain.kraken;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TickerDetails {
    @JsonProperty("a")
    private List<Object> a;
    @JsonProperty("b")

    private List<Object> b;
    @JsonProperty("c")

    private List<Object> c;
    @JsonProperty("v")

    private List<String> v;
    @JsonProperty("p")

    private List<String> p;
    @JsonProperty("t")

    private List<Integer> t;
    @JsonProperty("l")

    private List<String> l;
    @JsonProperty("h")

    private List<String> h;
    @JsonProperty("o")

    private List<String> o;

    // Getters and Setters
    // toString method
}
