package com.honeybee.honeybee;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Honey extends ImageView {
    private static final String HONEY_IMAGE_LOCATION = "/images/honey.png";

    public Honey(Pane parent) {
        String honeyPathLocation = getClass().getResource(HONEY_IMAGE_LOCATION).toString();
        setImage(new Image(honeyPathLocation));
        setFitWidth(30);
        setFitHeight(30);
        parent.getChildren().add(this);
        setX(Math.random() * (parent.getWidth() - getFitWidth()));
        setY(-getFitHeight());
    }

    public void update() {
        setY(getY() + 2); // Honeys fall down at a constant speed
    }
}