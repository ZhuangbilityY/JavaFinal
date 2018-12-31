package Individuals;

import javafx.animation.SequentialTransition;
import javafx.scene.image.*;
import java.util.Random;

public class CalabashBrother extends Hero{
    public enum Calabash {
        RED, ORG, YLW, GRN, CYN, BLU, PPL;
    }
    Calabash calabashCategory;

    public CalabashBrother(Calabash calabashCategory) {
        super();
        this.calabashCategory = calabashCategory;
        CombatEffectiveness = new Random().nextInt(20) + 50;
        switch (calabashCategory) {
            case RED: IndividualImage = new Image("Resources/RED.jpg"); Name = "Red"; Index = 1; break;
            case ORG: IndividualImage = new Image("Resources/ORG.jpg"); Name = "Org"; Index = 2; break;
            case YLW: IndividualImage = new Image("Resources/YLW.jpg"); Name = "Ylw"; Index = 3; break;
            case GRN: IndividualImage = new Image("Resources/GRN.jpg"); Name = "Grn"; Index = 4; break;
            case CYN: IndividualImage = new Image("Resources/CYN.jpg"); Name = "Cyn"; Index = 5; break;
            case BLU: IndividualImage = new Image("Resources/BLU.jpg"); Name = "Blu"; Index = 6; break;
            case PPL: IndividualImage = new Image("Resources/PPL.jpg"); Name = "Ppl"; Index = 7; break;
        }
        IndividualImageView = new ImageView(IndividualImage);
        MovementsList = new SequentialTransition(IndividualImageView);
    }

}
