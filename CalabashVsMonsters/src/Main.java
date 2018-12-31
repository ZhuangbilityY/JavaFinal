import Formations.BattleField;
import Formations.Formation;
import Individuals.Villian;
import Game.GameThread;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import Individuals.*;
import java.io.File;

import java.awt.event.ItemEvent;
import java.security.Key;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Calabash Vs Monsters");
        Group root = new Group();

        MenuBar menuBar = new MenuBar();
        EventHandler<ActionEvent> heroformationChange = changeHeroPlacement();
        EventHandler<ActionEvent> villianformationChange = changeVillianPlacement();

        Menu cFormation = new Menu("Calabash Formation");
        MenuItem cLongsnake = new MenuItem("Longsnake");
        cLongsnake.setOnAction(heroformationChange);
        cFormation.getItems().add(cLongsnake);
        MenuItem cCrane = new MenuItem("Crane");
        cCrane.setOnAction(heroformationChange);
        cFormation.getItems().add(cCrane);
        MenuItem cEchelon = new MenuItem("Echelon");
        cEchelon.setOnAction(heroformationChange);
        cFormation.getItems().add(cEchelon);
        MenuItem cYoke = new MenuItem("Yoke");
        cYoke.setOnAction(heroformationChange);
        cFormation.getItems().add(cYoke);
        MenuItem cScale = new MenuItem("Scale");
        cScale.setOnAction(heroformationChange);
        cFormation.getItems().add(cScale);
        MenuItem cSquare = new MenuItem("Square");
        cSquare.setOnAction(heroformationChange);
        cFormation.getItems().add(cSquare);
        MenuItem cCrescent = new MenuItem("Crescent");
        cCrescent.setOnAction(heroformationChange);
        cFormation.getItems().add(cCrescent);
        MenuItem cSpearhead = new MenuItem("Spearhead");
        cSpearhead.setOnAction(heroformationChange);
        cFormation.getItems().add(cSpearhead);

        Menu vFormation = new Menu("Villian Formation");
        MenuItem vLongsnake = new MenuItem("Longsnake");
        vLongsnake.setOnAction(villianformationChange);
        vFormation.getItems().add(vLongsnake);
        MenuItem vCrane = new MenuItem("Crane");
        vCrane.setOnAction(villianformationChange);
        vFormation.getItems().add(vCrane);
        MenuItem vEchelon = new MenuItem("Echelon");
        vEchelon.setOnAction(villianformationChange);
        vFormation.getItems().add(vEchelon);
        MenuItem vYoke = new MenuItem("Yoke");
        vYoke.setOnAction(villianformationChange);
        vFormation.getItems().add(vYoke);
        MenuItem vScale = new MenuItem("Scale");
        vScale.setOnAction(villianformationChange);
        vFormation.getItems().add(vScale);
        MenuItem vSquare = new MenuItem("Square");
        vSquare.setOnAction(villianformationChange);
        vFormation.getItems().add(vSquare);
        MenuItem vCrescent = new MenuItem("Crescent");
        vCrescent.setOnAction(villianformationChange);
        vFormation.getItems().add(vCrescent);
        MenuItem vSpearhead = new MenuItem("Spearhead");
        vSpearhead.setOnAction(villianformationChange);
        vFormation.getItems().add(vSpearhead);

        menuBar.getMenus().add(cFormation);
        menuBar.getMenus().add(vFormation);


        BorderPane borderPane = new BorderPane();

        //borderPane.prefHeightProperty().bind(scene.heightProperty());
        //borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setTop(menuBar);


        new BattleField();
        BattleField.initialize();


        borderPane.setCenter(BattleField.grid);
        root.getChildren().add(borderPane);

        GameThread game = new GameThread();


        //newgame.start();

        Scene scene = new Scene(root, BattleField.background.getWidth(), BattleField.background.getHeight() + 28);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                  @Override
                                  public void handle(KeyEvent event) {
                                      if(event.getCode() == KeyCode.SPACE) {
                                          if(!GameThread.InGame) {
                                              GameThread.InGame = true;
                                              game.startGame();
                                          }
                                      }
                                      else if(event.getCode() == KeyCode.L) {
                                          if(!GameThread.InGame) {
                                              FileChooser fileChooser = new FileChooser();
                                              File file = fileChooser.showOpenDialog(primaryStage);
                                              BattleField.initialize();
                                              GameThread.readGame(file);
                                              BattleField.setOriginScene();
                                              GameThread.AnimationList.play();
                                          }
                                      }
                                      else if(event.getCode() == KeyCode.S) {
                                          if(!GameThread.InGame) {
                                              FileChooser fileChooser = new FileChooser();
                                              File file = fileChooser.showSaveDialog(primaryStage);
                                              GameThread.saveGame(file);
                                              BattleField.initialize();
                                          }
                                      }

                                  }
                              }

        );


        primaryStage.setScene(scene);
        primaryStage.show();


    }






    private EventHandler<ActionEvent> changeHeroPlacement() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(GameThread.InGame)
                    return;
                MenuItem mItem = (MenuItem) event.getSource();
                String formtext = mItem.getText();

                switch (formtext) {
                    case "Longsnake": BattleField.heroformation = new Formation(Formation.Shape.Longsnake); break;
                    case "Crane": BattleField.heroformation = new Formation(Formation.Shape.Crane); break;
                    case "Echelon": BattleField.heroformation = new Formation(Formation.Shape.Echelon); break;
                    case "Yoke": BattleField.heroformation = new Formation(Formation.Shape.Yoke); break;
                    case "Scale": BattleField.heroformation = new Formation(Formation.Shape.Scale); break;
                    case "Square": BattleField.heroformation = new Formation(Formation.Shape.Square); break;
                    case "Crescent": BattleField.heroformation = new Formation(Formation.Shape.Crescent); break;
                    case "Spearhead": BattleField.heroformation = new Formation(Formation.Shape.Spearhead); break;
                }
                BattleField.initialize();
            }
        };
    }

    private EventHandler<ActionEvent> changeVillianPlacement() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(GameThread.InGame)
                    return;
                MenuItem mItem = (MenuItem) event.getSource();
                String formtext = mItem.getText();

                switch (formtext) {
                    case "Longsnake": BattleField.villianformation = new Formation(Formation.Shape.Longsnake); break;
                    case "Crane": BattleField.villianformation = new Formation(Formation.Shape.Crane); break;
                    case "Echelon": BattleField.villianformation = new Formation(Formation.Shape.Echelon); break;
                    case "Yoke": BattleField.villianformation = new Formation(Formation.Shape.Yoke); break;
                    case "Scale": BattleField.villianformation = new Formation(Formation.Shape.Scale); break;
                    case "Square":  BattleField.villianformation = new Formation(Formation.Shape.Square); break;
                    case "Crescent": BattleField.villianformation = new Formation(Formation.Shape.Crescent); break;
                    case "Spearhead": BattleField.villianformation = new Formation(Formation.Shape.Spearhead); break;
                }
                BattleField.initialize();

            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }



}
