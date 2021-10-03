package cz.baseball.zapisovatelecba.objects;

import lombok.Data;

@Data
public class League implements Comparable<League> {
    
    private String name;
    
    private int totalGames;
    private int unhandledGames;

    public League(String name) {
        this.name = name;
    }
    
    public void incTotalGames() {
        totalGames++;
    }
    
    public void incUnhandledGames() {
        unhandledGames++;
    }

    @Override
    public int compareTo(League o) {
        if (o != null) {
            return name.compareTo(o.name);
        } else {
            return 1;
        }
    }
    
}
