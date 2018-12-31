package Formations;
import java.io.Serializable;
import java.util.Vector;

public class Formation implements Serializable {
    public enum Shape {
        Longsnake, Crane, Echelon, Yoke, Scale, Square, Crescent, Spearhead
    }

    public Vector<Coordinates> getCoordinatesList() {
        return coordinatesList;
    }

    public Vector<Coordinates> coordinatesList;
    public Formation(Shape shape) {
        coordinatesList = new Vector<Coordinates>();
        switch (shape) {
            case Longsnake:
                coordinatesList.addElement(new Coordinates(0, 0));
                coordinatesList.addElement(new Coordinates(0, 1));
                coordinatesList.addElement(new Coordinates(0, 2));
                coordinatesList.addElement(new Coordinates(0, 3));
                coordinatesList.addElement(new Coordinates(0, 4));
                coordinatesList.addElement(new Coordinates(0, -1));
                coordinatesList.addElement(new Coordinates(0, -2));
                coordinatesList.addElement(new Coordinates(0, -3));
                break;
            case Crane:
                coordinatesList.add(new Coordinates(0, 2));
                coordinatesList.add(new Coordinates(-1, 1));
                coordinatesList.add(new Coordinates(-2, 0));
                coordinatesList.add(new Coordinates(-3, -1));
                coordinatesList.add(new Coordinates(1, 1));
                coordinatesList.add(new Coordinates(2, 0));
                coordinatesList.add(new Coordinates(3, -1));
                coordinatesList.add(new Coordinates(4, -2));
                break;
            case Echelon:
                coordinatesList.add(new Coordinates(0, 0));
                coordinatesList.add(new Coordinates(1, -1));
                coordinatesList.add(new Coordinates(2, -2));
                coordinatesList.add(new Coordinates(3, -3));
                coordinatesList.add(new Coordinates(4, -4));
                coordinatesList.add(new Coordinates(-1, 1));
                coordinatesList.add(new Coordinates(-2, 2));
                coordinatesList.add(new Coordinates(-3, 3));
                break;
            case Yoke:
                coordinatesList.add(new Coordinates(0, 0));
                coordinatesList.add(new Coordinates(1, 1));
                coordinatesList.add(new Coordinates(0, 2));
                coordinatesList.add(new Coordinates(1, 3));
                coordinatesList.add(new Coordinates(0, 4));
                coordinatesList.add(new Coordinates(1, -1));
                coordinatesList.add(new Coordinates(0, -2));
                coordinatesList.add(new Coordinates(1, -3));
                break;
            case Scale:
                coordinatesList.add(new Coordinates(0, 0));
                coordinatesList.add(new Coordinates(0, -1));
                coordinatesList.add(new Coordinates(0, -2));
                coordinatesList.add(new Coordinates(0, 1));
                coordinatesList.add(new Coordinates(-1, 0));
                coordinatesList.add(new Coordinates(1, 0));
                coordinatesList.add(new Coordinates(1, -1));
                coordinatesList.add(new Coordinates(2, 0));
                break;
            case Square:
                coordinatesList.add(new Coordinates(0, -2));
                coordinatesList.add(new Coordinates(-1, -1));
                coordinatesList.add(new Coordinates(-2, 0));
                coordinatesList.add(new Coordinates(-1, 1));
                coordinatesList.add(new Coordinates(0, 2));
                coordinatesList.add(new Coordinates(1, 1));
                coordinatesList.add(new Coordinates(2, 0));
                coordinatesList.add(new Coordinates(1, -1));
                break;
            case Crescent:
                coordinatesList.add(new Coordinates(0, 0));
                coordinatesList.add(new Coordinates(0, -1));
                coordinatesList.add(new Coordinates(0, 1));
                coordinatesList.add(new Coordinates(-1, 0));
                coordinatesList.add(new Coordinates(1, 1));
                coordinatesList.add(new Coordinates(1, -1));
                coordinatesList.add(new Coordinates(1, 2));
                coordinatesList.add(new Coordinates(1, -2));
                break;
            case Spearhead:
                coordinatesList.add(new Coordinates(0, -1));
                coordinatesList.add(new Coordinates(0, 0));
                coordinatesList.add(new Coordinates(0, 1));
                coordinatesList.add(new Coordinates(0, 2));
                coordinatesList.add(new Coordinates(-1, 0));
                coordinatesList.add(new Coordinates(-2, 1));
                coordinatesList.add(new Coordinates(1, 0));
                coordinatesList.add(new Coordinates(2, 1));
                break;
        }
    }

}
