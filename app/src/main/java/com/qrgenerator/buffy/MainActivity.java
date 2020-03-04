package com.qrgenerator.buffy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Resources resources;
    //best friend variables
    private CheckBox xander_box;
    private CheckBox willow_box;
    private CheckBox glory_box;
    private CheckBox cordelia_box;
    //sister variables
    private String darla;
    private String dawn;
    private String sister;
    //private string slayer variables
    private String kendra;
    private String nextSlayer;
    //watcher variables
    private String ethan;
    private String giles;
    private String watcher;
    //trio variables
    private CheckBox jonathan_box;
    private CheckBox andrew_box;
    private CheckBox warren_box;
    private CheckBox spike_box;
    //soul variables
    private CheckBox spike_box_2;
    private CheckBox angel_box;
    private CheckBox drusilla_box;
    private CheckBox darla_box_2;
    private int numberOfDeaths;
    //private ToggleButton submit_restart;
    private TextView[] questions = new TextView[7];
    private int points = 0;
    //first (default) radio buttons for each question
    private RadioButton sister_default;
    private RadioButton four_times_default;
    private RadioButton slayer_default;
    private RadioButton watcher_default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources = getResources();
        //strings
        darla = resources.getString(R.string.darla);
        dawn = resources.getString(R.string.dawn);
        kendra = resources.getString(R.string.kendra);
        ethan = resources.getString(R.string.ethan);
        giles = resources.getString(R.string.giles);

        //question variables
        questions[0] = findViewById(R.id.best_friend_question);
        questions[1] = findViewById(R.id.sister_question);
        questions[2] = findViewById(R.id.number_of_deaths_question);
        questions[3] = findViewById(R.id.slayer_question);
        questions[4] = findViewById(R.id.trio_names_question);
        questions[5] = findViewById(R.id.watcher_question);
        questions[6] = findViewById(R.id.soul_question);

        //radio button defaults
        sister_default = findViewById(R.id.sister_darla);
        four_times_default = findViewById(R.id.four);
        slayer_default = findViewById(R.id.next_slayer_kendra);
        watcher_default = findViewById(R.id.watcher_ethan);

        xander_box = findViewById(R.id.best_friend_xander);
        willow_box = findViewById(R.id.best_friend_willow);
        glory_box = findViewById(R.id.best_friend_glory);
        cordelia_box = findViewById(R.id.best_friend_cordelia);

        jonathan_box = findViewById(R.id.trio_jonathan);
        andrew_box = findViewById(R.id.trio_andrew);
        warren_box = findViewById(R.id.trio_warren);
        spike_box = findViewById(R.id.trio_spike);

        spike_box_2 = findViewById(R.id.soul_spike);
        angel_box = findViewById(R.id.soul_angel);
        drusilla_box = findViewById(R.id.soul_drusilla);
        darla_box_2 = findViewById(R.id.soul_darla);

        setDefaults();
    }

    public void setDefaults() {
        xander_box.setChecked(false);
        willow_box.setChecked(false);
        glory_box.setChecked(false);
        cordelia_box.setChecked(false);

        jonathan_box.setChecked(false);
        andrew_box.setChecked(false);
        warren_box.setChecked(false);
        spike_box.setChecked(false);

        spike_box_2.setChecked(false);
        angel_box.setChecked(false);
        drusilla_box.setChecked(false);
        darla_box_2.setChecked(false);

        sister = darla;
        sister_default.setChecked(true);

        numberOfDeaths = 4;
        four_times_default.setChecked(true);

        nextSlayer = kendra;
        slayer_default.setChecked(true);

        watcher = ethan;
        watcher_default.setChecked(true);
    }

    public void onSisterClicked(View view) {
        sister = ((RadioButton) view).getText().toString();
    }

    public void onNumberOfDeathsClicked(View view) {
        switch (view.getId()) {
            case R.id.twice:
                numberOfDeaths = 2;
                break;
            case R.id.three:
                numberOfDeaths = 3;
                break;
            case R.id.four:
                numberOfDeaths = 4;
                break;
            case R.id.five:
                numberOfDeaths = 5;
                break;
        }
    }

    public void onNextSlayerClicked(View view) {
        nextSlayer = ((RadioButton) view).getText().toString();
    }

    public void onWatcherClicked(View view) {
        watcher = ((RadioButton)view).getText().toString();
    }

    public void onSubmitRestartClicked(View view) {
        boolean checked = ((CompoundButton) view).isChecked();
        if (checked) {
            checkAnswers();
        }
        else {
            resetQuiz();
        }
    }

    public void checkBestFriendQuestion() {
        boolean correct = willow_box.isChecked() && xander_box.isChecked() &&
                !glory_box.isChecked() && !cordelia_box.isChecked();
        handleAnswer(correct,0);
    }

    public void checkSisterQuestion() {
        handleAnswer(sister.equals(dawn),1);
    }

    public void checkDeathsQuestion() {
        handleAnswer(numberOfDeaths == 2,2);
    }

    public void checkSlayerQuestion() {
        handleAnswer(nextSlayer.equals(kendra),3);
    }

    public void checkTrioQuestion() {
        boolean correct = andrew_box.isChecked() && jonathan_box.isChecked()
                && warren_box.isChecked() && !spike_box.isChecked();
        handleAnswer(correct,4);
    }

    public void checkWatcherQuestion() {
        handleAnswer(watcher.equals(giles),5);
    }

    public void checkSoulQuestion() {
        boolean correct = angel_box.isChecked() && spike_box_2.isChecked() &&
                !drusilla_box.isChecked() && !darla_box_2.isChecked();
        handleAnswer(correct,6);
    }

    public void checkAnswers() {
        checkBestFriendQuestion();
        checkSisterQuestion();
        checkDeathsQuestion();
        checkSlayerQuestion();
        checkTrioQuestion();
        checkWatcherQuestion();
        checkSoulQuestion();

        int total = 7;
        new AlertDialog.Builder(MainActivity.this)
            .setTitle("Score")
            .setMessage("You scored "+points+" out of "+ total +" points")
            .create().show();
    }

    public void resetQuiz() {
        setDefaults();
        points = 0;
        int defaultColor = resources.getColor(R.color.off_white);
        for (TextView question: questions) {
            question.setBackgroundColor(defaultColor);
        }
    }

    public void handleAnswer(boolean correct,int index) {
        if (correct) {
            points += 1;
            questions[index].setBackgroundColor(resources.getColor(R.color.green));
        }
        else {
            questions[index].setBackgroundColor(resources.getColor(R.color.red));
        }
    }
}
