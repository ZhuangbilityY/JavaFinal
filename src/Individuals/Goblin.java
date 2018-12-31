package Individuals;

import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Goblin extends Villian {
    public Goblin(int i) {
        super();
        Name = "Goblin" + i;
        Index = i + 1;
        CombatEffectiveness = new Random().nextInt(10) + 10;
        IndividualImage = new Image("Resources/Goblin.jpg");
        IndividualImageView = new ImageView(IndividualImage);
        MovementsList = new SequentialTransition(IndividualImageView);
    }
}