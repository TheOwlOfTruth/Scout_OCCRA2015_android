package org.team4384.scout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
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

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class ScoutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //As soon as the activity is created, it applies the username (see applyName() and getUserName()).
        applyName(getUserName());

        // Creates necessary references to prepare the choices for use in the spinner
        final Spinner spinner = (Spinner) findViewById(R.id.loadingMethodSpinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.loading_method_array, android.R.layout.simple_spinner_item);
        // Specifies the specific layout that the choices should follow
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Sets an OnClickListener to the button
        Button saveButton = (Button) findViewById(R.id.saveDataButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Declarations necessary to fix crashes when blanks are entered into the number slots
                final EditText editTeamNumber = (EditText) findViewById(R.id.teamNumber);
                final EditText editMatchNumber = (EditText) findViewById(R.id.matchNumber);
                final EditText editNumGameBalls = (EditText) findViewById(R.id.numGameBalls);
                //Parses the text from the fields to ensure that they are valid
                if (editTeamNumber.getText().toString().isEmpty() || editMatchNumber.getText().toString().isEmpty() || editNumGameBalls.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Make sure you filled out the whole form!", Snackbar.LENGTH_SHORT).show();
                } else {
                    saveScoutingData();
                    Snackbar.make(view, "Data has been saved.", Snackbar.LENGTH_SHORT).show();

                }
            }
        });



    }

    private String getUserName() {
        //Gets the name from the previous activity and returns it
        final SharedPreferences preferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        return preferences.getString("org.team4384.Scout.NAME", "");

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

        File folderLocation = new File(Environment.getExternalStorageDirectory() + File.separator +
                "Scout");
        try {
            if (!folderLocation.exists()) {

                folderLocation.mkdirs();

                File scoutingData = new File(folderLocation, "ScoutingData.xlsx");

                Workbook wb = new XSSFWorkbook();
                CreationHelper createHelper = wb.getCreationHelper();
                Sheet sheet = wb.createSheet("new sheet");

                // Create a row and put some cells in it. Rows are 0 based.
                Row row = sheet.createRow((short) 0);

                // Creates the headings for the excel file
                row.createCell(1).setCellValue(createHelper.createRichTextString("Team Number"));
                row.createCell(2).setCellValue(createHelper.createRichTextString("Match Number"));
                row.createCell(3).setCellValue(createHelper.createRichTextString("Number of Scored Game Balls"));
                row.createCell(4).setCellValue(createHelper.createRichTextString("Was Cue Ball Held?"));
                row.createCell(5).setCellValue(createHelper.createRichTextString("Was Eight Ball Held?"));
                row.createCell(6).setCellValue(createHelper.createRichTextString("Was Cue Ball Scored?"));
                row.createCell(7).setCellValue(createHelper.createRichTextString("Was Eight Ball Scored?"));
                row.createCell(8).setCellValue(createHelper.createRichTextString("Loading Method"));
                row.createCell(9).setCellValue(createHelper.createRichTextString("Can Hold Game Balls"));
                row.createCell(10).setCellValue(createHelper.createRichTextString("Can Hold Special Balls?"));
                row.createCell(11).setCellValue(createHelper.createRichTextString("Comments"));
                row.createCell(12).setCellValue(createHelper.createRichTextString("Scouter Name"));


                // Write the output to a file
                FileOutputStream fileOut = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "Scout" + File.separator + "ScoutingData.xlsx");
                wb.write(fileOut);
                fileOut.close();

                final SharedPreferences preferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("org.team4384.Scout.NEXT_EMPTY_ROW", 1);
                editor.apply();

            }
        }
        catch(IOException e){
            Snackbar.make(findViewById(R.id.snackbarlocation), "There was an error. Please try again", Snackbar.LENGTH_SHORT);
        }

        try {
            // Once confirming that the file exists, creates references to the workbook to commence data writing.
            File dataPath = new File(Environment.getExternalStorageDirectory() + File.separator + "Scout" + File.separator + "ScoutingData.xlsx");
            FileInputStream dataLocationStream = new FileInputStream(dataPath);
            XSSFWorkbook wb = new XSSFWorkbook(dataLocationStream);
            Sheet dataSheet = wb.getSheetAt(0);


            //Finds the next empty row, based on the NEXT_EMPTY_ROW preference
            final SharedPreferences preferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
            int nextEmptyRow = preferences.getInt("org.team4384.Scout.NEXT_EMPTY_ROW", 2);




            //Since the row is empty, create it.
            Row row = dataSheet.createRow(nextEmptyRow);

            //inputs the data into the excel file
            row.createCell(1).setCellValue(teamNumber);
            row.createCell(2).setCellValue(matchNumber);
            row.createCell(3).setCellValue(numGameBalls);
            row.createCell(4).setCellValue(wasCueBallHeld);
            row.createCell(5).setCellValue(wasEightBallHeld);
            row.createCell(6).setCellValue(wasCueBallScored);
            row.createCell(7).setCellValue(wasEightBallScored);
            row.createCell(8).setCellValue(loadingMethod);
            row.createCell(9).setCellValue(canHoldGameBalls);
            row.createCell(10).setCellValue(canHoldSpecialBalls);
            row.createCell(11).setCellValue(comments);
            row.createCell(12).setCellValue(scouterName);

            FileOutputStream fileOut = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "Scout" + File.separator + "ScoutingData.xlsx");
            wb.write(fileOut);
            fileOut.close();

            nextEmptyRow += 1;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("org.team4384.Scout.NEXT_EMPTY_ROW", nextEmptyRow);
            editor.apply();


        }
        catch (IOException e) {
            Snackbar.make(findViewById(R.id.snackbarlocation), "There was an error. Please try again.", Snackbar.LENGTH_SHORT).show();
        }

    }

}
