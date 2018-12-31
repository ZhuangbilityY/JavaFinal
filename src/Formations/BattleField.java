package Formations;


import Game.Action;
import Game.GameThread;
import Individuals.*;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Random;
import java.util.Vector;


public class BattleField {

    public BattleField() {
        hero_left = 8;
        villian_left = 8;

        DuringMove = new Object();
        stance = new Integer[N][M];

        heroformation = new Formation(Formation.Shape.Longsnake);
        villianformation = new Formation(Formation.Shape.Longsnake);

        grid = new GridPane();

        background = new Image("Resources/background.jpg");
        boxWidth = background.getWidth() / N;
        boxHeight = background.getHeight() / M;

        grid.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));

        initialize();

    }

    public static void initialize() {

        for(int i = 0; i < BattleField.N; i++)
            for(int j = 0; j < BattleField.M; j++)
                BattleField.stance[i][j] = 0;

        heros = new Hero[8];
        heros[0] = new Grandpa();
        heros[1] = new CalabashBrother(CalabashBrother.Calabash.RED);
        heros[2] = new CalabashBrother(CalabashBrother.Calabash.ORG);
        heros[3] = new CalabashBrother(CalabashBrother.Calabash.YLW);
        heros[4] = new CalabashBrother(CalabashBrother.Calabash.GRN);
        heros[5] = new CalabashBrother(CalabashBrother.Calabash.CYN);
        heros[6] = new CalabashBrother(CalabashBrother.Calabash.BLU);
        heros[7] = new CalabashBrother(CalabashBrother.Calabash.PPL);

        villians = new Villian[8];
        villians[0] = new Scorpion();
        villians[1] = new Snake();
        villians[2] = new Goblin(1);
        villians[3] = new Goblin(2);
        villians[4] = new Goblin(3);
        villians[5] = new Goblin(4);
        villians[6] = new Goblin(5);
        villians[7] = new Goblin(6);

        setOriginScene();

        GameThread.InGame = false;
        GameThread.ActionList = new Vector<Action>();
        GameThread.AnimationList = new SequentialTransition();
    }

    public static void setOriginScene() {
        grid.getChildren().clear();

        for(int i = 0; i < BattleField.N; i++)
            grid.getColumnConstraints().add(new ColumnConstraints(BattleField.boxWidth));
        for(int i = 0; i < BattleField.M; i++)
            grid.getRowConstraints().add(new RowConstraints(BattleField.boxHeight));

        BattleField.setHeroFormation(BattleField.heroformation.getCoordinatesList());
        BattleField.setVillianFormation(BattleField.villianformation.getCoordinatesList());

        for(int i = 0; i < 8; i++) {
            Hero h = BattleField.heros[i];
            grid.add(h.getIndividualImageView(), h.getPosition().getX(), h.getPosition().getY());
            Villian v = BattleField.villians[i];
            grid.add(v.getIndividualImageView(), v.getPosition().getX(), v.getPosition().getY());
        }
    }

    public static void setHeroFormation(Vector<Coordinates> coordinatesList) {
        for(int i = 0; i < 8; i++) {
            Coordinates absolutePos = new Coordinates(coordinatesList.get(i).getX() + 4, coordinatesList.get(i).getY() + 4);
            heros[i].initPosition(absolutePos);
        }
    }

    public static void setVillianFormation(Vector<Coordinates> coordinatesList) {
        for(int i = 0; i < 8; i++) {
            Coordinates absolutePos = new Coordinates(coordinatesList.get(i).getX() + 15, coordinatesList.get(i).getY() + 4);
            villians[i].initPosition(absolutePos);
        }
    }

    public synchronized static Individual getIndividualOnSpot(Coordinates pos) {
        for(int i = 0; i < 8; i++) {
            if(heros[i].getPosition().getX() == pos.getX() && heros[i].getPosition().getY() == pos.getY() && heros[i].isAlive()) {
                return heros[i];
            }
            if(villians[i].getPosition().getX() == pos.getX() && villians[i].getPosition().getY() == pos.getY() && villians[i].isAlive()) {
                return villians[i];
            }
        }

        return new Individual();
    }

    public static void killIndividualOnSpot(Coordinates pos) {
        for(int i = 0; i < 8; i++) {
            if(heros[i].getPosition().getX() == pos.getX() && heros[i].getPosition().getY() == pos.getY()) {
                heros[i].setAlive(false);
                break;
            }
            if(villians[i].getPosition().getX() == pos.getX() && villians[i].getPosition().getY() == pos.getY()) {
                villians[i].setAlive(false);
                break;
            }
        }

    }

    public static void outputStance() {
        System.out.println("stance");
        for(int j = 0; j < 10; j++) {
            for(int i = 0; i < 20; i ++) {
                System.out.print(BattleField.stance[i][j]);
            }
            System.out.println();
        }
    }

    public static void countLeft() {
        int h = 0, v = 0;
        for(int i = 0; i < 8; i++) {
            if(heros[i].isAlive())
                h ++;
            if(villians[i].isAlive())
                v++;
        }
        System.out.println("hero " + h + " villian " + v);
    }

    public static void updateStance() {
        for(int i = 0; i < 8; i++) {
            if(heros[i].isAlive())
                stance[heros[i].getPosition().getX()][heros[i].getPosition().getY()] = heros[i].getMyStance();
            if(villians[i].isAlive())
                stance[villians[i].getPosition().getX()][villians[i].getPosition().getY()] = villians[i].getMyStance();
        }

    }

    public static int N = 20;
    public static int M = 10;
    public static Integer[][] stance;

    public static Hero[] heros;
    public static Villian[] villians;
    public static int hero_left;
    public static int villian_left;

    public static Formation heroformation;
    public static Formation villianformation;

    public static GridPane grid;

    public static Image background;

    public static double boxWidth;
    public static double boxHeight;

    public static Object DuringMove;
}
