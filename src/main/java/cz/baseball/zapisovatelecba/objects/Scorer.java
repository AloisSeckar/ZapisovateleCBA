package cz.baseball.zapisovatelecba.objects;

import lombok.Data;

@Data
public class Scorer implements Comparable<Scorer>  {
    
    private String name;
    
    private int gamesU18;
    private int gamesU21;
    private int gamesLIG;
    private int gamesEXL;

    public Scorer(String name) {
        this.name = name;
    }
    
    public void incGamesU18() {
        gamesU18++;
    }
    
    public void incGamesU21() {
        gamesU21++;
    }
    
    public void incGamesLIG() {
        gamesLIG++;
    }
    
    public void incGamesEXL() {
        gamesEXL++;
    }

    @Override
    public int compareTo(Scorer o) {
        if (o != null) {
            return name.compareTo(o.name);
        } else {
            return 1;
        }
    }
    
}
