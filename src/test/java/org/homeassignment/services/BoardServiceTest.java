package org.homeassignment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.homeassignment.Main;
import org.homeassignment.model.Board;
import org.homeassignment.model.Configuration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class BoardServiceTest {

    @Test
    public void board_generated_correctly() throws IOException {
        ObjectMapper parser = new ObjectMapper();
        URL resource = Main.class.getClassLoader().getResource("config.json");
        Configuration configuration = parser.readValue(resource, Configuration.class);;
        BoardService boardService = new BoardService(configuration);
        Board board = boardService.generateBoard();

        assertNotNull(board);
        assertNotNull(board.getMatrix());
        assertEquals(configuration.getColumns(), board.getMatrix().size());
        assertNotNull(board.getStandardSymbols());
        assertNotNull(board.getBonusSymbols());
    }

    @Test
    public void board_generation_error_parsing_config_file() {

        assertThrows(IOException.class, () -> {
            ObjectMapper parser = new ObjectMapper();
            URL resource = Main.class.getClassLoader().getResource("config_error.json");
            parser.readValue(resource, Configuration.class);
        });
    }
}