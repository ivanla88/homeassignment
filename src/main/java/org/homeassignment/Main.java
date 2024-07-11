package org.homeassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.homeassignment.model.Result;
import org.homeassignment.services.GameService;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Main
{
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );

    public static void main( String[] args ) throws IOException {
        String configFile = "";
        int bettingAmount = 0;
        int i = 0;
        while (i < args.length) {

            if (args[i].equals("--config")) {
                configFile = args[i + 1];
            } else if (args[i].equals("--betting-amount")) {
                bettingAmount = Integer.valueOf(args[i + 1]);
            }
            i += 2;
        }

        LOGGER.info( "Starting application with: " );
        LOGGER.info( "- Config file: " + configFile);
        LOGGER.info( "- Betting amount: " + bettingAmount);

        try {
            GameService service = new GameService(configFile, bettingAmount);
            Result result = service.checkBet();
            ObjectMapper mapper = new ObjectMapper();
            LOGGER.info(mapper.writeValueAsString(result));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error parsing config file");
        }
    }
}
