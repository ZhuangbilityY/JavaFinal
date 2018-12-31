package Game;

import Individuals.Individual;

import java.io.Serializable;

public class Action implements Serializable {
    protected int Stance;
    protected int Act;
    protected int MyIndex;
    protected int EnemyIndex;
    protected int ByX;
    protected int ByY;

    public Action(int stance, int act, int myIndex, int enemyIndex, int byX, int byY) {
        Stance = stance;
        Act = act;
        MyIndex = myIndex;
        EnemyIndex = enemyIndex;
        ByX = byX;
        ByY = byY;
    }
}
