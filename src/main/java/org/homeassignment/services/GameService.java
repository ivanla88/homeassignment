package org.homeassignment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.homeassignment.Main;
import org.homeassignment.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static org.homeassignment.model.enums.ConditionType.*;
import static org.homeassignment.model.enums.ImpactTypes.*;

public class GameService {

    private Configuration configuration;
    private BoardService boardService;
    private int betAmount;

    public GameService(String configFile, int betAmount) throws IOException {
        ObjectMapper parser = new ObjectMapper();
        URL resource = Main.class.getClassLoader().getResource(configFile);
        this.configuration = parser.readValue(resource, Configuration.class);;
        this.betAmount = betAmount;
        boardService = new BoardService(this.configuration);
    }

    public Result checkBet() {
        Board boardInfo = boardService.generateBoard();

        Map<String, List<String>> winningCombinations = checkCombinations(boardInfo);

        String bonusName = null;
        if (!winningCombinations.isEmpty() && boardInfo.getBonusSymbols() != null) {
            String symbolName = boardInfo.getBonusSymbols().getLeft();
            bonusName = MISS.equals(symbolName) ? null : symbolName;
        }
        Double reward = (winningCombinations.isEmpty()) ? Double.valueOf(0) : calculateReward(winningCombinations, bonusName);

        return new Result(boardInfo.getMatrix(), Integer.valueOf(reward.intValue()).toString(), winningCombinations, bonusName);
    }

    private Double calculateReward(Map<String, List<String>> winningCombinations, String bonusName) {

        Double total = Double.valueOf(0);
        List<Double> rewards = new ArrayList<>();

        for (Entry combination : winningCombinations.entrySet()) {

            Symbol symbol = this.configuration.getSymbols().get(combination.getKey());
            List<String> combinations = (List<String>) combination.getValue();
            List<Combination> combInfo = combinations.stream().map(c -> this.configuration.getWinCombinations().get(c)).collect(Collectors.toList());

            Double reward = this.betAmount * symbol.getMultiplier();
            for (Combination c: combInfo) {
                reward *= c.getMultiplier();
            }
            rewards.add(reward);
        }
        total = rewards.stream().reduce(Double.valueOf(0), Double::sum);
        if (bonusName != null) {
            Symbol bonusSymbol = this.configuration.getSymbols().get(bonusName);
            if (MULTIPLY_REWARD.equals(bonusSymbol.getImpact())) {
                total *= bonusSymbol.getMultiplier();
            } else if (EXTRA_BONUS.equals(bonusSymbol.getImpact())) {
                total += bonusSymbol.getExtra();
            }
        }
        return total;
    }


    private Map<String, List<String>> checkCombinations(Board boardInfo) {

        Map<String, List<String>> winningCombinations = new HashMap<>();
        Map<String, Combination> availableCombinations = this.configuration.getWinCombinations();

        for (Entry<String, Combination> entry : availableCombinations.entrySet()) {
            Combination combination = entry.getValue();

            List<String> symbolsMatching = new ArrayList<>();
            if (SAME_SYMBOLS.equals(combination.getCondition())) {

                symbolsMatching = boardInfo.getStandardSymbols().entrySet().stream()
                        .filter(ent -> ent.getValue() == combination.getAmount())
                        .map(e -> e.getKey())
                        .collect(Collectors.toList());

            } else if (LINEAR_SYMBOLS.equals(combination.getCondition())) {

                List<List<String>> coveredAreas = combination.getCoveredAreas();
                for (List line : coveredAreas) {
                    String symbol = checkLine(boardInfo.getMatrix(), line);
                    if (symbol != null) {
                        symbolsMatching.add(symbol);
                    }
                }
            }

            for (String symbol : symbolsMatching) {

                List<String> existing = winningCombinations.get(symbol);
                if (existing == null) {
                    List<String> combinations = new ArrayList<>();
                    combinations.add(entry.getKey());
                    winningCombinations.put(symbol, combinations);
                } else {
                    existing.add(entry.getKey());
                    winningCombinations.put(symbol, existing);
                }
            }
        }

        return winningCombinations;
    }

    private String checkLine(List<List<String>> board, List<String> line) {

        String previous = "";
        for (String element : line) {
            String[] indexes = element.split(":");
            Integer column = Integer.valueOf(indexes[0]);
            Integer row = Integer.valueOf(indexes[1]);
            String value = board.get(column).get(row);

            if (previous.isEmpty()) {
                previous = value;
            } else {
                if (!previous.equals(value)) {
                    return null;
                }
            }
        }
        return previous;
    }
}
