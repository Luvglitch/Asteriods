package asteroids;

import java.util.Random;

import javafx.scene.shape.Polygon;

public class Asteroid extends Character{
    private double rotationalMovement;

    public Asteroid(int x, int y){
        super(new PolygonFactory().createPolygon(), x, y);

        Random random = new Random();
        this.getCharacter().setRotate(random.nextInt(360));

        int accelerationAmount = 1 + random.nextInt(10);

        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        rotationalMovement = 0.5 - random.nextDouble();
    }

    @Override
    public void move(){
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }
}
