package Individuals;

import Formations.BattleField;
import Formations.Coordinates;
import Game.Action;
import Game.GameThread;
import Formations.Formation;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.swing.text.Position;
import java.util.Random;

public class Individual implements Runnable{
    public Individual() {
        Name = "";
        Index = -1;
        Alive = true;
        CombatEffectiveness = 0;
        OriginPosition = new Coordinates(0, 0);
        FormerPosition = new Coordinates(0, 0);
        Position = new Coordinates(0, 0);
        MovementsList = new SequentialTransition();
        CurrentRound = 0;
        MyStance = 0;
        DuringMove = new Object();
    }

    public int getCombatEffectiveness() {
        return CombatEffectiveness;
    }

    public Coordinates getPosition() { return Position; }
    public void updatePosition(Coordinates pos) {
        FormerPosition = Position;
        this.Position = pos;
        BattleField.stance[FormerPosition.getX()][FormerPosition.getY()] = 0;
        BattleField.stance[Position.getX()][Position.getY()] = MyStance;
    }
    public void initPosition(Coordinates pos) {
        OriginPosition = pos;
        FormerPosition = pos;
        Position = pos;
        BattleField.stance[pos.getX()][pos.getY()] = MyStance;
    }

    public void setAlive(boolean alive) { this.Alive = alive; }
    public Image getIndividualImage() { return IndividualImage; }
    public ImageView getIndividualImageView() { return IndividualImageView; }
    public int getCurrentRound() { return CurrentRound; }
    public void setCurrentRound(int round) { CurrentRound = round; }
    public boolean isAlive() { return Alive; }
    public SequentialTransition getMovementsList() { return MovementsList; }
    public String getName() {return Name; }
    public int getMyStance() { return MyStance; }

    public void run() {
        while(BattleField.hero_left > 0 && BattleField.villian_left > 0) {


            synchronized (BattleField.DuringMove) {
                if(!Alive) {
                    //System.out.println(Name + " dead");
                    return;
                }
                if(BattleField.hero_left == 0 || BattleField.villian_left == 0)
                    break;
                Coordinates destination = getDestination();
                if(BattleField.stance[destination.getX()][destination.getY()] == 0) {
                    updatePosition(destination);
                    addMoveAnimation();
                }
                else if(BattleField.stance[destination.getX()][destination.getY()] == 3 - MyStance) {
                    Individual enemy = BattleField.getIndividualOnSpot(destination);
                    addFightAnimation(enemy);
                    fight(enemy);
                    //System.out.println("Hero " + BattleField.hero_left + " Villian " + BattleField.villian_left);
                    //BattleField.countLeft();
                }
                BattleField.updateStance();

             }
            Thread.yield();

        }
        //System.out.println(Name + " over " + Alive);
        if(!GameThread.Flag) {
            GameThread.Flag= true;
            GameThread.InGame = false;
            GameThread.AnimationList.play();
        }

    }

    public void fight(Individual enemy) {
        if(battle(enemy)) {
            enemy.setAlive(false);
            BattleField.stance[enemy.getPosition().getX()][enemy.getPosition().getY()] = 0;
            enemy.addDeadAnimation();

            //System.out.println(Name + " successfully kill " + enemy.getName());
            //BattleField.outputStance();

            if(enemy.getMyStance() == 1)
                BattleField.hero_left --;
            else
                BattleField.villian_left --;
        }
        else {
            //System.out.println(Name + " get killed by " + enemy.getName());
            Alive = false;
            BattleField.stance[Position.getX()][Position.getY()] = 0;
            addDeadAnimation();

            //BattleField.outputStance();

            if(enemy.getMyStance() == 1)
                BattleField.villian_left --;
            else
                BattleField.hero_left --;
        }
    }


    public boolean battle(Individual enemy) {
        int res = new Random().nextInt(getCombatEffectiveness() + enemy.getCombatEffectiveness());
        if(res < getCombatEffectiveness())
            return true;
        else
            return false;
    }


    public Coordinates getDestination() {
        int distance = 30;
        Coordinates enemy = new Coordinates(0, 0);
        if(MyStance == 1) {
            for(int i = 0; i < 8; i ++) {
                int dx = Math.abs(BattleField.villians[i].getPosition().getX() - Position.getX());
                int dy = Math.abs(BattleField.villians[i].getPosition().getY() - Position.getY());
                if(dx + dy < distance && BattleField.villians[i].isAlive()) {
                    distance = dx + dy;
                    enemy = BattleField.villians[i].getPosition();
                }
            }

        }
        else {
            for (int i = 0; i < 8; i++) {
                int dx = Math.abs(BattleField.heros[i].getPosition().getX() - Position.getX());
                int dy = Math.abs(BattleField.heros[i].getPosition().getY() - Position.getY());
                if (dx + dy < distance && BattleField.heros[i].isAlive()) {
                    distance = dx + dy;
                    enemy = BattleField.heros[i].getPosition();
                }
            }

        }

        Coordinates destination;
        int dx = enemy.getX() - Position.getX();
        int dy = enemy.getY() - Position.getY();

        if(Math.abs(dx) > Math.abs(dy)) {
            if(dx > 0)
                destination = new Coordinates(Position.getX() + 1, Position.getY());
            else
                destination = new Coordinates(Position.getX() - 1, Position.getY());
        }
        else {
            if(dy > 0)
                destination = new Coordinates(Position.getX(), Position.getY() + 1);
            else
                destination = new Coordinates(Position.getX(), Position.getY() - 1);
        }
        return destination;

    }


    public void addMoveAnimation() {

        GameThread.AnimationList.getChildren().add(
                MoveAnimation(this, Position.getX() - FormerPosition.getX(), Position.getY() - FormerPosition.getY())
        );
        GameThread.ActionList.add(
                new Action(MyStance, 1,Index, -1, Position.getX() - FormerPosition.getX(), Position.getY() - FormerPosition.getY())
        );
    }

    public void addFightAnimation(Individual enemy) {
        GameThread.AnimationList.getChildren().add(FightAnimation(this, enemy));
        GameThread.ActionList.add(new Action(MyStance, 2, Index, enemy.Index, 0, 0));

    }

    public void addDeadAnimation() {
        GameThread.AnimationList.getChildren().add(DeadAnimation(this));
        GameThread.ActionList.add(new Action(MyStance, 3, Index, -1, 0, 0));

    }


    public static TranslateTransition MoveAnimation(Individual individual, int deviateX, int deviateY) {
        TranslateTransition move = new TranslateTransition(Duration.millis(500), individual.getIndividualImageView());

        move.setByX(deviateX * BattleField.boxWidth);
        move.setByY(deviateY * BattleField.boxHeight);

        move.setCycleCount(1);
        move.setAutoReverse(false);

        return move;
    }

    public static ParallelTransition FightAnimation(Individual p1, Individual p2) {
        ScaleTransition p1st = new ScaleTransition(Duration.millis(500), p1.getIndividualImageView());
        p1st.setByX(2f);
        p1st.setByY(2f);
        p1st.setCycleCount(2);
        p1st.setAutoReverse(true);

        ScaleTransition p2st = new ScaleTransition(Duration.millis(500), p2.getIndividualImageView());
        p2st.setByX(2f);
        p2st.setByY(2f);
        p2st.setCycleCount(2);
        p2st.setAutoReverse(true);

        ParallelTransition fightanimation = new ParallelTransition();
        fightanimation.getChildren().addAll(p1st, p2st);

        return fightanimation;
    }

    public static ParallelTransition DeadAnimation(Individual individual) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), individual.getIndividualImageView());
        ft.setFromValue(1.0f);
        ft.setToValue(0f);

        RotateTransition rt = new RotateTransition(Duration.millis(500), individual.getIndividualImageView());
        rt.setByAngle(180f);

        ParallelTransition dead = new ParallelTransition();
        dead.getChildren().addAll(ft, rt);

        return dead;
    }

    protected String Name;
    protected int Index;
    protected int CombatEffectiveness;
    protected Coordinates OriginPosition;
    protected Coordinates FormerPosition;
    protected Coordinates Position;
    protected Image IndividualImage;
    protected ImageView IndividualImageView;
    protected boolean Alive;
    protected SequentialTransition MovementsList;
    protected int CurrentRound;
    protected int MyStance;
    protected Object DuringMove;
}








