package asteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {

    public static int WIDTH = 300;
    public static int HEIGHT = 200;
    @Override
    public void start(Stage stage){
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        List<Asteroid> asteroids = new ArrayList<>();
        List<Projectile> projectiles = new ArrayList<>();

        Text text = new Text(10, 20, "Points: 0");
        AtomicInteger points = new AtomicInteger();

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Asteroid asteroid = new Asteroid(random.nextInt(WIDTH/3),random.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }


        pane.getChildren().add(ship.getCharacter());
        pane.getChildren().add(text);
        asteroids.stream().forEach((asteroid) -> {
            asteroid.getCharacter().setRotate(random.nextInt(40));
            asteroid.accelerate();
            asteroid.accelerate();
            pane.getChildren().add(asteroid.getCharacter());
        });

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        pane.setOnKeyPressed((event) -> 
        pressedKeys.put(event.getCode(), true));

        pane.setOnKeyReleased((event) -> 
        pressedKeys.put(event.getCode(), false));

        

        new AnimationTimer() {
            
            public void handle(long now){
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)){
                    ship.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)){
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                if(pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 3){
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());

                    projectiles.add(projectile);
                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));
                    pane.getChildren().add(projectile.getCharacter());
                }

                asteroids.forEach( (asteroid) -> {
                        if(ship.collided(asteroid)){
                        stop();
                        }
                    });

                ship.move();
                asteroids.forEach((asteroid) -> asteroid.move());
                projectiles.forEach((projectile) -> projectile.move());


                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if(projectile.collided(asteroid)){
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    if(!projectile.isAlive()){
                        text.setText("Points: " + points.addAndGet(1000) );
                    }
                });

                removeDeadCharacters(projectiles, pane);
                removeDeadCharacters(asteroids, pane);

                if(Math.random() < 0.005){
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if(!asteroid.collided(ship)){
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }

            }
        }.start();
        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
        pane.setFocusTraversable(true);
        pane.requestFocus(); 
    }
    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }

    public static int partsCompleted() {
        // State how many parts you have completed using the return value of this method
        return 4;
    }

    public void removeDeadCharacters(Collection<? extends Character> characters, Pane pane){
        characters.removeIf(c -> {
            if (c.isAlive()) {
                return false;
            }

    
            pane.getChildren().remove(c.getCharacter());
            return true;
        });
    }

}
