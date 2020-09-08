package main.model.graphics.sceneElements.buttons;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import main.model.jobSystem.Job;

import java.util.Observable;
import java.util.Observer;

public class JobButton extends Button implements Observer {
    private Job job;
    private boolean highlighted;
    private Color buttonColor = Color.SEASHELL;
    private Color highlightedButtonColor = Color.PINK;
    private Background defaultBackground = new Background(new BackgroundFill(buttonColor, new CornerRadii(5), new Insets(0)));
    private Background highlightedBackground = new Background(new BackgroundFill(highlightedButtonColor, new CornerRadii(5), new Insets(0)));

    public JobButton(String text, Job job) {
        this.setText(text);
        this.setBackground(defaultBackground);
        this.setId("normalNode");
        this.job = job;
    }

    public Job getJob() {
        return this.job;
    }

    public void highlightButton() {
        this.setBackground(highlightedBackground);
        highlighted = true;
    }

    public void unHighlightButton() {
        this.setBackground(defaultBackground);
        highlighted = false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals(this.job)) {
            highlightButton();
        } else if (highlighted) unHighlightButton();
    }
}
