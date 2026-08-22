package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Character {
    private Polygon character;
    private Point2D movement;
    private boolean isAlive;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Character(Polygon character,int x, int y){
        this.character = character;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0.0, 0.0);
        this.isAlive = true;
    }

    public void turnLeft(){
        this.character.setRotate(this.character.getRotate() - 3);
    }

    public void turnRight(){
        this.character.setRotate(this.character.getRotate() + 3);
    }

    public void move(){
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());


        if(this.character.getTranslateX() < 0){
            this.character.setTranslateX(this.character.getTranslateX() + AsteroidsApplication.WIDTH);
        }

        if(this.character.getTranslateX() > AsteroidsApplication.WIDTH){
            this.character.setTranslateX((this.character.getTranslateX() % AsteroidsApplication.WIDTH));
        }

        if(this.character.getTranslateY() < 0){
            this.character.setTranslateY(this.character.getTranslateY() + AsteroidsApplication.HEIGHT);
        }

        if (this.character.getTranslateY() > AsteroidsApplication.HEIGHT) {
            this.character.setTranslateY(this.character.getTranslateY() % AsteroidsApplication.HEIGHT);
        }
    }

    public void accelerate(){
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.005;
        changeY *= 0.005;

        this.movement = this.movement.add(changeX, changeY);
    }


    public Polygon getCharacter(){
        return this.character;
    }

    public boolean collided(Character otherCharacter){
        Shape intersection = Shape.intersect(this.character, otherCharacter.character);

        return intersection.getBoundsInLocal().getWidth() != -1;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D value){
        this.movement = value;
    }

}
