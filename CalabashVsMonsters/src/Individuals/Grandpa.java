package Individuals;

import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Grandpa extends Hero {
    public Grandpa() {
        super();
        Name = "Grandpa";
        Index = 0;
        CombatEffectiveness = new Random().nextInt(5) + 5;
        IndividualImage = new Image("Resources/Grandpa.jpg");
        IndividualImageView = new ImageView(IndividualImage);
        MovementsList = new SequentialTransition(IndividualImageView);
    }

}