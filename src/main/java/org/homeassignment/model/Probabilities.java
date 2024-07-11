package org.homeassignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Probabilities {

    @JsonProperty("standard_symbols")
    private List<StandardProbability> standarSymbols;

    @JsonProperty("bonus_symbols")
    private BonusProbability bonusSymbols;
}
