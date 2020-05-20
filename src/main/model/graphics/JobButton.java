package main.model.graphics;

import javafx.scene.control.Button;
import main.model.jobSystem.Job;

public class JobButton extends Button {
    private Job job;

    public JobButton(String text, Job job) {
        this.setText(text);
        this.job = job;
    }

    public Job getJob() {
        return this.job;
    }
}
