package main.model.graphics.sceneElements.images;

import javafx.scene.control.Label;
import main.model.jobSystem.Job;

public class CharacterJobLabel extends Label {

    public CharacterJobLabel(Job job, int minXSize, int minYSize) {
        this.setText(job.getJobTitle());
        this.setPrefSize(minXSize, minYSize);
    }
}
