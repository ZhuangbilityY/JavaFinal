package Individuals;

import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Scorpion extends Villian {
    public Scorpion() {
        super();
        Name = "Scorpion";
        Index = 0;
        CombatEffectiveness = new Random().nextInt(20) + 60;
        IndividualImage = new Image("Resources/Scorpion.jpg");
        IndividualImageView = new ImageView(IndividualImage);
        MovementsList = new SequentialTransition(IndividualImageView);
    }

}