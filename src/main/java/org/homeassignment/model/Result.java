package org.homeassignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {

    private List<List<String>>matrix;

    private String reward;

    @JsonProperty("applied_winning_combinations")
    private Map<String, List<String>> winningCombinations;

    @JsonProperty("applied_bonus_symbol")
    private String bonus;
}
