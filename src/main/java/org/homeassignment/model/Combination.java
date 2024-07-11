package org.homeassignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.homeassignment.model.enums.ConditionType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Combination {

    @JsonProperty("reward_multiplier")
    private Double multiplier;

    @JsonProperty("when")
    private ConditionType condition;

    @JsonProperty("count")
    private Integer amount;

    private String group;

    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;

}
