package com.honeybee.honeybee;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Bee extends ImageView {
    private static final String BEE_IMAGE_LOCATION = "/images/bee.png";

    public Bee(Pane parent) {
        String beePathLocation = getClass().getResource(BEE_IMAGE_LOCATION).toString();

        setImage(new Image(beePathLocation));
        setFitWidth(30);
        setFitHeight(30);
        parent.getChildren().add(this);
        setX(Math.random() * (parent.getWidth() - getFitWidth()));
        setY(-getFitHeight());
    }

    public void update() {
        setY(getY() + 4); // Bees fall down faster than honeys
    }
}