package org.homeassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.graalvm.collections.Pair;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Board {

    List<List<String>> matrix;
    Map<String, Integer> standardSymbols;
    Pair<String, Integer> bonusSymbols;
}
