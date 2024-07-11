package org.homeassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardProbability {

    private int row;
    private int column;
    private Map<String, Integer> symbols;
}
