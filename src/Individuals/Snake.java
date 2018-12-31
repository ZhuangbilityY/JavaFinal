package Individuals;

import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Snake extends Villian {
    public Snake() {
        super();
        Name = "Snake";
        Index = 1;
        CombatEffectiveness = new Random().nextInt(20) + 70;
        IndividualImage = new Image("Resources/Snake.jpg");
        IndividualImageView = new ImageView(IndividualImage);
        MovementsList = new SequentialTransition(IndividualImageView);
    }

}
