package org.homeassignment.services;

import lombok.AllArgsConstructor;
import org.graalvm.collections.Pair;
import org.homeassignment.model.Board;
import org.homeassignment.model.Configuration;
import org.homeassignment.model.Probabilities;
import org.homeassignment.model.Symbol;

import java.util.*;
import java.util.Map.Entry;

import static org.homeassignment.model.enums.SymbolTypes.*;

@AllArgsConstructor
public class BoardService {

    private Configuration configuration;

    public Board generateBoard() {

        boolean symbolGenerated = false;
        List<List<String>> board = new ArrayList<>();
        Map<String, Integer> standardSymbols = new HashMap<>();
        Pair<String, Integer> bonusSymbols = null;

        for (int i = 0; i < this.configuration.getColumns(); i++) {
            List<String> element = new ArrayList<>();
            for (int j = 0; j < this.configuration.getRows(); j++) {
                Entry<String, Symbol> entry = generateSymbol(i, j, symbolGenerated);
                element.add(entry.getKey());
                Symbol symbol = entry.getValue();
                if (BONUS.equals(symbol.getType())) {
                    symbolGenerated = true;
                    bonusSymbols = Pair.create(entry.getKey(), Integer.valueOf(1));
                } else if (STANDARD.equals(symbol.getType())) {
                    Integer value = standardSymbols.get(entry.getKey());
                    if (value != null) {
                        value += 1;
                    } else {
                        value = Integer.valueOf(1);
                    }
                    standardSymbols.put(entry.getKey(), value);
                }
            }
            board.add(element);
        }
        return new Board(board, standardSymbols, bonusSymbols);
    }

    private Entry<String, Symbol> generateSymbol(int col, int row, boolean symbolGenerated) {

        Map<String, Double> listProbs = new HashMap<String, Double>();
        Probabilities probs = this.configuration.getProbabilities();

        Map<String, Integer> standardSymbols = probs.getStandarSymbols().stream()
                .filter(el -> el.getColumn() == col && el.getRow() == row)
                .findFirst().get().getSymbols();

        Double total = standardSymbols.values().stream().reduce(0, Integer::sum).doubleValue();
        Double minProb = Double.valueOf(1);
        for (Map.Entry<String, Integer> entry: standardSymbols.entrySet()) {
            Double symbolProbabilty = Double.valueOf(entry.getValue()) / total;
            listProbs.put(entry.getKey(), symbolProbabilty);
            if (symbolProbabilty < minProb.doubleValue()) {
                minProb = Double.valueOf(symbolProbabilty);
            }
        }

        if (!symbolGenerated) {
            Map<String, Integer> bonusSymbols = probs.getBonusSymbols().getSymbols();
            for (Map.Entry<String, Integer> entry : bonusSymbols.entrySet()) {
                listProbs.put(entry.getKey(), minProb);
            }
        }

        Double totalProbs = listProbs.values().stream().reduce(Double.valueOf(0), Double::sum);
        Random rand = new Random();
        double randomValue = rand.nextDouble() * totalProbs.doubleValue();
        for (Entry<String, Double> entry : listProbs.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue <= 0) {
                return this.configuration.getSymbols().entrySet().stream().filter(el -> el.getKey().equals(entry.getKey())).findFirst().get();
            }
        }

        return null;
    }
}
