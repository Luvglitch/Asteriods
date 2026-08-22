package asteroids;

import java.util.Random;

import javafx.scene.shape.Polygon;

public class PolygonFactory {
    public Polygon createPolygon(){
        Polygon polygon = new Polygon();
        Random random = new Random();

        double size = 10 + random.nextInt(10); 
        double c1 = Math.cos(2 * Math.PI / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(2 * Math.PI / 5);
        double s2 = Math.sin(4 * Math.PI / 5);

        polygon.getPoints().addAll(size, 0.0,
                                            size * c1, -1 * size * s1,
                                            -1 * size * c2, -1 * size * s2,
                                            -1 * size *c2, size * s2,
                                            size * c1, size * s1); 

        for(int i = 0; i < polygon.getPoints().size(); i++){
            int change = random.nextInt(5) - 2;
            polygon.getPoints().set(i, polygon.getPoints().get(i) + change);
        }

        return polygon;
    }
}
