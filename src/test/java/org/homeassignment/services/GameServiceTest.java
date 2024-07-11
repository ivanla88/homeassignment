package org.homeassignment.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.graalvm.collections.Pair;
import org.homeassignment.Main;
import org.homeassignment.model.Board;
import org.homeassignment.model.Configuration;
import org.homeassignment.model.Result;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @Test
    public void generated_correctly() throws IOException {

        GameService gameService = new GameService("config.json", 100);
        assertNotNull(gameService);
    }

    @Test
    public void generation_error_parsing_config_file() {

        assertThrows(IOException.class, () -> {
            ObjectMapper parser = new ObjectMapper();
            URL resource = Main.class.getClassLoader().getResource("config_error.json");
            parser.readValue(resource, Configuration.class);
        });
    }

    @Test
    public void same_symbol_3_times() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("A", "B", "F"),
                Arrays.asList("E", "A", "5x"),
                Arrays.asList("A", "D", "C")
        );
        Map<String, Integer> standard = Map.of("A", 3, "B", 1, "C", 1, "D", 1, "E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("5x", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals("5x", result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("25000", result.getReward());

        assertEquals(1, result.getWinningCombinations().size());
        List<Entry<String, List<String>>> entries = result.getWinningCombinations().entrySet().stream().toList();
        assertEquals("A", entries.get(0).getKey());
        assertEquals("same_symbol_3_times", entries.get(0).getValue().get(0));
    }

    @Test
    public void same_symbol_5_times() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("A", "A", "F"),
                Arrays.asList("E", "A", "A"),
                Arrays.asList("A", "5x", "C")
        );
        Map<String, Integer> standard = Map.of("A", 5, "C", 1,  "E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("5x", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals("5x", result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("50000", result.getReward());

        assertEquals(1, result.getWinningCombinations().size());
        List<Entry<String, List<String>>> entries = result.getWinningCombinations().entrySet().stream().toList();
        assertEquals("A", entries.get(0).getKey());
        assertEquals("same_symbol_5_times", entries.get(0).getValue().get(0));
    }

    @Test
    public void same_symbol_3_times_and_same_symbols_diagonally_left_to_right() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("A", "B", "F"),
                Arrays.asList("E", "A", "C"),
                Arrays.asList("B", "5x", "A")
        );
        Map<String, Integer> standard = Map.of("A", 3,"B", 1, "C", 1,  "E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("5x", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals("5x", result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("125000", result.getReward());

        assertEquals(1, result.getWinningCombinations().size());
        List<Entry<String, List<String>>> entries = result.getWinningCombinations().entrySet().stream().toList();
        assertEquals("A", entries.get(0).getKey());
        assertEquals("same_symbol_3_times", entries.get(0).getValue().get(0));
        assertEquals("same_symbols_diagonally_left_to_right", entries.get(0).getValue().get(1));
    }

    @Test
    public void same_symbol_3_times_and_same_symbols_diagonally_right_to_left() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("F", "B", "A"),
                Arrays.asList("E", "A", "C"),
                Arrays.asList("A", "5x", "B")
        );
        Map<String, Integer> standard = Map.of("A", 3,"B", 1, "C", 1,  "E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("5x", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals("5x", result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("125000", result.getReward());

        assertEquals(1, result.getWinningCombinations().size());
        List<Entry<String, List<String>>> entries = result.getWinningCombinations().entrySet().stream().toList();
        assertEquals("A", entries.get(0).getKey());
        assertEquals("same_symbol_3_times", entries.get(0).getValue().get(0));
        assertEquals("same_symbols_diagonally_right_to_left", entries.get(0).getValue().get(1));
    }

    @Test
    public void same_symbols_vertically() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("F", "B", "A"),
                Arrays.asList("E", "C", "A"),
                Arrays.asList("B", "5x", "A")
        );
        Map<String, Integer> standard = Map.of("A", 3,"B", 1, "C", 1,  "E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("5x", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals("5x", result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("50000", result.getReward());

        assertEquals(1, result.getWinningCombinations().size());
        List<Entry<String, List<String>>> entries = result.getWinningCombinations().entrySet().stream().toList();
        assertEquals("A", entries.get(0).getKey());
        assertEquals("same_symbol_3_times", entries.get(0).getValue().get(0));
        assertEquals("same_symbols_vertically", entries.get(0).getValue().get(1));
    }

    @Test
    public void same_symbols_horizontally() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("F", "B", "C"),
                Arrays.asList("A", "A", "A"),
                Arrays.asList("E", "5x", "B")
        );
        Map<String, Integer> standard = Map.of("A", 3,"B", 1, "C", 1,  "E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("5x", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals("5x", result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("50000", result.getReward());

        assertEquals(1, result.getWinningCombinations().size());
        List<Entry<String, List<String>>> entries = result.getWinningCombinations().entrySet().stream().toList();
        assertEquals("A", entries.get(0).getKey());
        assertEquals("same_symbol_3_times", entries.get(0).getValue().get(0));
        assertEquals("same_symbols_horizontally", entries.get(0).getValue().get(1));
    }

    @Test
    public void more_than_one_symbol() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("F", "A", "C"),
                Arrays.asList("A", "A", "C"),
                Arrays.asList("E", "+1000", "C")
        );
        Map<String, Integer> standard = Map.of("A", 3, "C", 3,  "E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("+1000", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals("+1000", result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("8000", result.getReward());

        assertEquals(2, result.getWinningCombinations().size());
        List<Entry<String, List<String>>> entries = result.getWinningCombinations().entrySet().stream().toList();
        assertEquals("A", entries.get(0).getKey());
        assertEquals("same_symbol_3_times", entries.get(0).getValue().get(0));
        assertEquals("C", entries.get(1).getKey());
        assertEquals("same_symbol_3_times", entries.get(1).getValue().get(0));
        assertEquals("same_symbols_vertically", entries.get(1).getValue().get(1));
    }

    @Test
    public void no_winning() throws IOException, IllegalAccessException {

        BoardService boardService = mock(BoardService.class);
        GameService gameService = new GameService("config.json", 100);
        Field field = ReflectionUtils
                .findFields(GameService.class, f -> f.getName().equals("boardService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(gameService, boardService);

        List<List<String>> matrix = Arrays.asList(
                Arrays.asList("A", "B", "C"),
                Arrays.asList("D", "E", "F"),
                Arrays.asList("B", "+1000", "A")
        );
        Map<String, Integer> standard = Map.of("A", 2, "B", 2,"C", 1, "D", 1 ,"E", 1, "F", 1);
        Pair<String, Integer> bonus = Pair.create("+1000", 1);
        Board board = new Board(matrix, standard, bonus);

        when(boardService.generateBoard()).thenReturn(board);

        Result result = gameService.checkBet();

        assertEquals(null, result.getBonus());
        assertEquals(matrix, result.getMatrix());
        assertEquals("0", result.getReward());
        assertEquals(0, result.getWinningCombinations().size());
    }
}