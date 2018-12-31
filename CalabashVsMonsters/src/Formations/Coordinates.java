package Formations;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean validCoordinate() {
        if(x < 0 || x >= BattleField.N || y < 0 || y >= BattleField.M)
            return false;
        else
            return true;
    }


    public int getX(){ return x;}
    public int getY() {return y;}

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    private int x;
    private int y;
}
