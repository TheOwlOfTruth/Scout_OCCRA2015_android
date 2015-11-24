package org.team4384.scout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ScoutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creates necessary references.
        final Spinner spinner = (Spinner) findViewById(R.id.loadingMethodSpinner);


        //As soon as the activity is created, it applies the username (see applyName() and getUserName()).
        applyName(getUserName());


        // Creates an ArrayAdapter to prepare the choices for use in the spinner
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.loading_method_array, android.R.layout.simple_spinner_item);
        // Specifies the specific layout that the choices should follow
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Button saveButton = (Button) findViewById(R.id.saveDataButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Declarations necessary to fix crashes when blanks are entered into the number slots
                final EditText editTeamNumber = (EditText) findViewById(R.id.teamNumber);
                final EditText editMatchNumber = (EditText) findViewById(R.id.matchNumber);
                final EditText editNumGameBalls = (EditText) findViewById(R.id.numGameBalls);
                //Parses the text from the fields to ensure that they are valid
                if (editTeamNumber.getText().toString().isEmpty()||editMatchNumber.getText().toString().isEmpty()||editNumGameBalls.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Make sure you filled out the whole form!", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    Snackbar.make(view, "Saving Data...", Snackbar.LENGTH_LONG).show();
                    saveScoutingData();
                    Snackbar.make(view, "Data Saved.", Snackbar.LENGTH_SHORT).show();

                }
            }
        });
    }

    private String getUserName() {
        //Gets the name from the previous activity and returns it
        final SharedPreferences preferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        return preferences.getString("org.team4384.Scout.NAME","");

    }

    private void applyName(String userName) {
        //Applies the userName given in the previous activity into the textView at the top of this page.
        //Gets a reference to the TextView
        final TextView scrollText = (TextView) findViewById(R.id.scrollText);

        //Creates the text to be displayed
        final String greetText = "Hi, " + userName + "!\nFill out some basic info and let's get started!";

        //Displays the greeting created above.
        scrollText.setText(greetText);

    }

    private void saveScoutingData() {
        //Takes the scouting data from the session and saves it to the locally stored excel file
        //Gets a reference to all of the entry fields
        final EditText editTeamNumber = (EditText) findViewById(R.id.teamNumber);
        final EditText editMatchNumber = (EditText) findViewById(R.id.matchNumber);
        final EditText editNumGameBalls = (EditText) findViewById(R.id.numGameBalls);
        final CheckBox checkCueBallHeld = (CheckBox) findViewById(R.id.cueBallHeldBool);
        final CheckBox checkEightBallHeld = (CheckBox) findViewById(R.id.eightBallHeldBool);
        final CheckBox checkCueBallScored = (CheckBox) findViewById(R.id.cueBallScoredBool);
        final CheckBox checkEightBallScored = (CheckBox) findViewById(R.id.eightBallScoredBool);
        final Spinner spinnerLoadingMethod = (Spinner) findViewById(R.id.loadingMethodSpinner);
        final CheckBox checkCanHoldGameBalls = (CheckBox) findViewById(R.id.canHoldGameBall);
        final CheckBox checkCanHoldSpecialBalls = (CheckBox) findViewById(R.id.canHoldSpecialBall);
        final EditText editComments = (EditText) findViewById(R.id.userComments);

        //takes the user's inputs and stores them as variables, ready to add to the actual spreadsheet.
        final Integer teamNumber = Integer.parseInt(editTeamNumber.getText().toString());
        final Integer matchNumber = Integer.parseInt(editMatchNumber.getText().toString());
        final Integer numGameBalls = Integer.parseInt(editNumGameBalls.getText().toString());
        final Boolean wasCueBallHeld = checkCueBallHeld.isChecked();
        final Boolean wasEightBallHeld = checkEightBallHeld.isChecked();
        final Boolean wasCueBallScored = checkCueBallScored.isChecked();
        final Boolean wasEightBallScored = checkEightBallScored.isChecked();
        final String loadingMethod = spinnerLoadingMethod.getSelectedItem().toString();
        final Boolean canHoldGameBalls = checkCanHoldGameBalls.isChecked();
        final Boolean canHoldSpecialBalls = checkCanHoldSpecialBalls.isChecked();
        final String comments = editComments.getText().toString();
        final String scouterName = getUserName();

        //TODO
        //tests for a workbook

    }

    private void exportScoutingData() {
        //TODO
        //Exports the scouting data as an excel file visible to the outside world.
    }
}
