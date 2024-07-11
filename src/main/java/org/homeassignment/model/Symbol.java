package org.homeassignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.homeassignment.model.enums.ImpactTypes;
import org.homeassignment.model.enums.SymbolTypes;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Symbol {

    @JsonProperty("reward_multiplier")
    private Double multiplier;
    private Double extra;
    private SymbolTypes type;
    private ImpactTypes impact;

}
