package cz.baseball.zapisovatelecba;

import java.net.URI;
import java.net.http.*;
import org.apache.commons.lang3.StringUtils;

public class Main {

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.baseball.cz/zapisovatele/nominace/"))
                    .build();

            HttpResponse<String> response
                    = client.send(request, HttpResponse.BodyHandlers.ofString());

            StatsObject stats = new StatsObject();
            response.body().lines().forEach(line -> {
                if (line.contains("<tr class")) {
                    stats.openLine();
                } else if (line.contains("</tr>")) {
                    stats.closeLine();
                }
                if (stats.isLineOpened()) {
                    if (line.contains("<td")) {
                        stats.incCellsEncountered();
                    }
                    if (stats.getCellsEncountered() == 1) {
                        stats.setLeagueName(stripCellTags(line));
                    } else if (stats.getCellsEncountered() == 10) {
                        String scorerName = stripCellTags(line);
                        if (isHumanName(scorerName)) {
                            scorerName = reverseScorerName(scorerName);
                        }
                        stats.addGame(scorerName);
                    }
                }
            });

            System.out.println("SOUTĚŽE");
            System.out.println("=======");
            System.out.println();

            System.out.print("SOUTĚŽ\t");
            System.out.print("ZÁPASY\t");
            System.out.println("NOMINACE");
            System.out.println("-----------------------");

            stats.getLeagues().values().stream().sorted().forEach(record -> {
                System.out.print(record.getName().toUpperCase() + "\t");

                int total = record.getTotalGames();
                int unhandled = record.getUnhandledGames();
                int handled = total - unhandled;

                System.out.println(total + "\t" + handled + "/" + total);
            });

            System.out.println();

            System.out.println("ZAPISOVATELÉ");
            System.out.println("============");
            System.out.println();

            System.out.print(StringUtils.rightPad("JMENO", 26, " ") + "\t");
            System.out.print("EXL\t");
            System.out.print("LIG\t");
            System.out.print("U21\t");
            System.out.print("U18\t");
            System.out.print("TOT\t");
            System.out.println("$$$");
            System.out.println("--------------------------------------------------------------------------");

            stats.getScorers().values().stream().sorted().forEach(record -> {
                String name = record.getName().toUpperCase();
                System.out.print(StringUtils.rightPad(name, 26, " ") + "\t");

                int exl = record.getGamesEXL();
                int lig = record.getGamesLIG();
                int u21 = record.getGamesU21();
                int u18 = record.getGamesU18();

                int total = exl + lig + u21 + u18;

                int reward = (exl + lig) * 700 + (u21 + u18) * 500;

                System.out.print(exl + "\t" + lig + "\t" + u21 + "\t" + u18 + "\t");
                System.out.println(total + "\t" + reward);
            });

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static String stripCellTags(String line) {
        line = line.substring(0, line.indexOf("</td>"));
        line = line.substring(line.indexOf(">") + 1);
        return line;
    }

    private static boolean isHumanName(String scorerName) {
        return !scorerName.contains("Hluboká") && !scorerName.contains("SaBaT");
    }

    private static String reverseScorerName(String scorerName) {
        String[] scorer = scorerName.split(" ");
        scorerName = scorer[0];
        if (scorer.length > 1) {
            scorerName = scorer[1] + " " + scorerName;
        }
        return scorerName;
    }

}
