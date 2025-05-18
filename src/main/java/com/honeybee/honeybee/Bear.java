package com.honeybee.honeybee;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Bear extends ImageView {
    private static final String BEAR_IMAGE_LOCATION = "/images/bear.png";
    private static final double BEAR_SPEED = 5.0;

    public Bear(Pane parent) {
        String bearPathLocation = getClass().getResource(BEAR_IMAGE_LOCATION).toString();
        setImage(new Image(bearPathLocation));
        setFitWidth(50);
        setFitHeight(50);
        parent.getChildren().add(this);
        setX(parent.getWidth() / 2 - getFitWidth() / 2);
        setY(parent.getHeight() - getFitHeight() - 10);
    }

    public void move(KeyCode keyCode) {
        switch (keyCode) {
            case LEFT:
                if (getX() - BEAR_SPEED >= 100) {
                    setX(getX() - BEAR_SPEED);
                }
                break;
            case RIGHT:
                if (getX() + BEAR_SPEED <= getParent().getLayoutBounds().getWidth() - getFitWidth()) {
                    setX(getX() + BEAR_SPEED);
                }
                break;
        }
    }
}