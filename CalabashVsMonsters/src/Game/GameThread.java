package Game;

import Formations.BattleField;
import Formations.Coordinates;
import Formations.Formation;
import Individuals.Hero;
import Individuals.Individual;
import Individuals.Villian;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import java.io.*;
import java.util.*;


public class GameThread {

    public GameThread() {
        AnimationList = new SequentialTransition();
        ActionList = new Vector<Action>();
        InGame = false;
        Flag = false;
    }

    public static void saveGame(File file) {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(BattleField.heroformation);
            out.writeObject(BattleField.villianformation);
            //out.writeObject((Integer)ActionList.size());
            out.writeObject(ActionList);
            out.close();
            fileOut.close();
            System.out.println("save success");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }


    public static void readGame(File file) {
        try
        {
            FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            BattleField.heroformation = (Formation)in.readObject();
            BattleField.villianformation = (Formation) in.readObject();
            //Integer num = (Integer) in.readObject();
            ActionList = (Vector<Action>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("read success");
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("class not found");
            c.printStackTrace();
            return;
        }

        AnimationList.getChildren().clear();
        for(int i = 0; i < ActionList.size(); i++) {
            //System.out.println("lu ru " + i);
            Action action = ActionList.get(i);
            if(action.Stance == 1) {
                switch (action.Act) {
                    case 1:
                        AnimationList.getChildren().add(Individual.MoveAnimation(BattleField.heros[action.MyIndex], action.ByX, action.ByY));
                        break;
                    case 2:
                        AnimationList.getChildren().add(Individual.FightAnimation(BattleField.heros[action.MyIndex], BattleField.villians[action.EnemyIndex]));
                        break;
                    case 3:
                        AnimationList.getChildren().add(Individual.DeadAnimation(BattleField.heros[action.MyIndex]));
                        break;
                }
            }
            else {
                switch (action.Act) {
                    case 1:
                        AnimationList.getChildren().add(Individual.MoveAnimation(BattleField.villians[action.MyIndex], action.ByX, action.ByY));
                        break;
                    case 2:
                        AnimationList.getChildren().add(Individual.FightAnimation(BattleField.villians[action.MyIndex], BattleField.heros[action.EnemyIndex]));
                        break;
                    case 3:
                        AnimationList.getChildren().add(Individual.DeadAnimation(BattleField.villians[action.MyIndex]));
                        break;
                }

            }


        }
        //System.out.println(AnimationList.getChildren().size());

    }



    public void startGame() {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 8; i ++)
            list.add(i);
        Collections.shuffle(list);
        for(int e : list) {
            new Thread(BattleField.heros[e]).start();
            new Thread(BattleField.villians[e]).start();
        }

    }

    public static boolean InGame;
    public static boolean Flag;
    public static SequentialTransition AnimationList;
    public static Vector<Action> ActionList;
}
