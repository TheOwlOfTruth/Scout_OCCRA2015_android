package org.team4384.scout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NamePrompt extends AppCompatActivity implements View.OnClickListener {

    //Declaration of the button
    Button scoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_prompt);
        //Creates a reference to the main button on the screen, and then sets an OnClickListener on it.
        scoutButton = (Button) findViewById(R.id.scoutButton);
        scoutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //If the button clicked is the scoutButton:
            case R.id.scoutButton:
                //Creates a reference to the name blank, and then gets the string in the blank from it.
                final EditText nameEnter = (EditText) findViewById(R.id.nameEnter);
                final String userName = nameEnter.getText().toString();

                //Saves the name to the preferences section for further use later on.
                final String preferenceKey = "org.team4384.Scout.NAME";
                final SharedPreferences preferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(preferenceKey, userName);
                editor.apply();



                //Creates an intent (used to start activities)
                final Intent intent = new Intent(this, ScoutingActivity.class);
                //launches the main scouting activity
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}

