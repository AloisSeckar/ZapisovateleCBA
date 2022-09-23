package cz.baseball.zapisovatelecba;

import cz.baseball.zapisovatelecba.objects.*;
import java.util.HashMap;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
public class StatsObject {
    
    @Setter(AccessLevel.NONE)
    private boolean lineOpened = false;
    @Setter(AccessLevel.NONE)
    private int cellsEncountered = 0;
    
    private String leagueName;
    
    @Setter(AccessLevel.NONE)
    private final HashMap<String, Scorer> scorers = new HashMap<>();
    @Setter(AccessLevel.NONE)
    private final HashMap<String, League> leagues = new HashMap<>();
    
    public void openLine() {
        lineOpened = true;
        cellsEncountered = 0;
    }
    
    public void closeLine() {
        lineOpened = false;
    }
    
    public void incCellsEncountered() {
        cellsEncountered++;
    }
    
    public void addGame(String scorerName) {
        League leagueRecord = leagues.get(leagueName);
        if (leagueRecord == null) {
            leagueRecord = new League(leagueName);
            leagues.put(leagueName, leagueRecord);
        }
        
        leagueRecord.incTotalGames();
        
        if (StringUtils.isNotBlank(scorerName)) {
            Scorer scorerRecord = scorers.get(scorerName);
            if (scorerRecord == null) {
                scorerRecord = new Scorer(scorerName);
                scorers.put(scorerName, scorerRecord);
            }

            switch (leagueRecord.getName()) {
                case "Exl":
                case "NExl":
                    scorerRecord.incGamesEXL();
                    break;
                case "LIG":
                case "NLig":
                    scorerRecord.incGamesLIG();
                    break;
                case "U21":
                    scorerRecord.incGamesU21();
                    break;
                case "U18":
                    scorerRecord.incGamesU18();
                    break;
                case "CBP":
                    // no action - CBP scoring not being paid by CBA
                    break;
                default:
                    System.out.println("WARN: No action available for league='" + leagueName + "'");
            }
        } else {
            leagueRecord.incUnhandledGames();
        }
    }
    
}
