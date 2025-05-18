package com.honeybee.honeybee;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HoneyBeeGame extends Application {

    private Bear bear;
    private final List<Honey> honeys = new ArrayList<>();
    private final List<Bee> bees = new ArrayList<>();
    private int score = 0;
    private Label scoreLabel;
    private Label gameOverLabel;
    private Button restartButton;
    private AnimationTimer gameLoop;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hungry Bear Game");

        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        bear = new Bear(root);

        // Initialize the score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setStyle("-fx-font-size: 24px;");
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);
        root.getChildren().add(scoreLabel);

        // Initialize the game over label
        gameOverLabel = new Label("Game Over!");
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setStyle("-fx-font-size: 48px;");
        gameOverLabel.setLayoutX(300);
        gameOverLabel.setLayoutY(250);
        gameOverLabel.setVisible(false);
        root.getChildren().add(gameOverLabel);

        // Initialize the restart button
        restartButton = new Button("Restart Game");
        restartButton.setLayoutX(350);
        restartButton.setLayoutY(350);
        restartButton.setVisible(false);
        restartButton.setOnAction(e -> restartGame(root));
        root.getChildren().add(restartButton);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
                bear.move(event.getCode());
            }
        });

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    updateGame();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        gameLoop.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateGame() throws Exception {
        // Spawn honeys and bees
        if (Math.random() < 0.01) {
            honeys.add(new Honey((Pane) bear.getParent()));
        }
        if (Math.random() < 0.01) {
            bees.add(new Bee((Pane) bear.getParent()));
        }

        startCollisionDetectionOfBearWithHoney();
        startCollisionDetectionOfBeeWithBear();
    }

    public void startCollisionDetectionOfBearWithHoney() {
        // Collision detection for honey and bear.
        Iterator<Honey> honeyIterator = honeys.iterator();
        while (honeyIterator.hasNext()) {
            Honey honey = honeyIterator.next();
            honey.update();
            Bounds honeyBoundsInParent = honey.getBoundsInParent();
            Bounds bearBoundsInParent = bear.getBoundsInParent();
            if (honeyBoundsInParent.intersects(bearBoundsInParent)) {
                honeyIterator.remove();
                honey.setImage(null);
                score += 10;
                updateScore();
                System.out.println("Score: " + score);
            } else if (honey.getY() > honey.getParent().getLayoutBounds().getHeight()) {
                honeyIterator.remove();
                honey.setImage(null);
            }
        }
    }

    public void startCollisionDetectionOfBeeWithBear() throws Exception {
        // Collision detection for bee and bear.
        Iterator<Bee> beeIterator = bees.iterator();
        while (beeIterator.hasNext()) {
            Bee bee = beeIterator.next();
            bee.update();
            Bounds beeBoundsInParent = bee.getBoundsInParent();
            Bounds bearBoundsInParent = bear.getBoundsInParent();
            if (beeBoundsInParent.intersects(bearBoundsInParent)) {
                beeIterator.remove();
                bee.setImage(null);
                System.out.println("Game Over! Final Score: " + score);
                gameOver();
            } else if (bee.getY() > bee.getParent().getLayoutBounds().getHeight()) {
                beeIterator.remove();
                bee.setImage(null);
            }
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    private void gameOver() {
        gameLoop.stop();
        gameOverLabel.setVisible(true);
        restartButton.setVisible(true);
    }

    private void restartGame(Pane root) {
        honeys.clear();
        bees.clear();
        root.getChildren().removeIf(node -> node instanceof Honey || node instanceof Bee);
        score = 0;
        updateScore();
        gameOverLabel.setVisible(false);
        restartButton.setVisible(false);
        gameLoop.start();
    }
}