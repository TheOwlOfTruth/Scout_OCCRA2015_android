package org.team4384.scout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ScoutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        applyUserName();


        }

    private void applyUserName() {
        //Applies the userName given in the previous activity into the textView at the top of this page.

        //Gets a reference to the TextView
        TextView scrollText = (TextView) findViewById(R.id.scrollText);

        //Gets the name from the previous activity
        SharedPreferences preferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String userName = preferences.getString("org.team4384.Scout.NAME","");

        //Creates the text to be displayed
        String greetText = "Hi, " + userName + "! Fill out some basic information and let's get started!";
        scrollText.setText(greetText);

    }
}
