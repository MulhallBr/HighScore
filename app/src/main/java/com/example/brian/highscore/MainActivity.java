package com.example.brian.highscore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.brian.highscore.model.Score;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends Activity {

    public ArrayList<Score> high_scores = new ArrayList<>();

    // Use a Constant for the file name and clearing fields.
    private static final String FILE_NAME = "high_scores.txt";
    private static final String CLEAR = "";

    // Declare the View fields
    TextView txtDisplayScore;
    TextView txtDisplayBy;

    EditText edtName;
    EditText edtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Views
        edtName = findViewById(R.id.edt_name);
        edtScore = findViewById(R.id.edt_score);

        txtDisplayScore = findViewById(R.id.txt_display_score);
        txtDisplayBy = findViewById(R.id.txt_display_by);

        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitScore();
            }
        });

        Button viewAllScores = findViewById(R.id.btn_view_scores);
        viewAllScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDisplayActivity();
            }
        });

        Button resetScores = findViewById(R.id.btn_reset_scores);
        resetScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile(FILE_NAME);

                txtDisplayScore.setText(CLEAR);
                txtDisplayBy.setText(CLEAR);
                edtScore.setText(null);
                edtName.setText(null);

                high_scores.clear();

                read();
            }
        });

        read();
        displayHighScore();

    }

    // This method displays the highest score when the App starts
    // Method is also called when the Submit button is pressed (inside submitScore method)
    private void displayHighScore() {

        Collections.sort(high_scores);  // Sorts the high score ArrayList

        txtDisplayBy.setText(high_scores.get(0).getName()); // Sets the appropriate name

        Integer tempScore = high_scores.get(0).getScore();
        txtDisplayScore.setText(tempScore.toString());      // Sets the appropriate score

        if (txtDisplayBy.getText().toString().isEmpty()) {  // If there is no score to display,
            txtDisplayScore.setText(CLEAR);                 // they are set to blank/null values.
        }

    }

    // Method is called when the View All Scores button is pressed
    private void openDisplayActivity() {

        Intent intent = new Intent(getApplicationContext(), DisplayActivity.class); // Create a new intent

        intent.putParcelableArrayListExtra("score", high_scores);   // Create's a parcelable out of the high scores list and sends it with the intent.

        startActivity(intent);  // Start the new Activity

    }

    // Method is called when the Submit button is pressed
    private void submitScore() {

        // Create required variables
        String score = edtScore.getText().toString();
        String name = edtName.getText().toString();

        // IF STATEMENT IN ORDER TO CHECK ALL COMBINATIONS OF USER ERRORS
        if (score.isEmpty() && name.isEmpty()) {

            (edtScore = findViewById(R.id.edt_score)).requestFocus();
            (edtScore = findViewById(R.id.edt_score)).setError(getString(R.string.valid_score));

            (edtName = findViewById(R.id.edt_name)).requestFocus();
            (edtName = findViewById(R.id.edt_name)).setError(getString(R.string.valid_name));

        } else {

            if (score.isEmpty()) {

                (edtScore = findViewById(R.id.edt_score)).requestFocus();
                (edtScore = findViewById(R.id.edt_score)).setError(getString(R.string.valid_score));

            } else if (name.isEmpty()) {

                (edtName = findViewById(R.id.edt_name)).requestFocus();
                (edtName = findViewById(R.id.edt_name)).setError(getString(R.string.valid_name));

            } else {

                int scoreInt = Integer.parseInt(score); // Set the score String to an int

                write(scoreInt, name);  // Call the write method to write to the file

                high_scores.clear();    // Clear the high scores list, so that it can be made again, anew

                read();     // Read the file, now with the new score added (storing scores in the array)

                displayHighScore(); // Display the highest score in the file (now in the array)

                edtScore.setText(null); // Clear the EditText views
                edtName.setText(null);
            }
        }
    }

    // Method is called when the submit button is pressed (inside submitScore method)
    public void write(int arg_score, String arg_name) {

        // Declare the OUTPUT stream
        FileOutputStream fos;

        try {

            fos = openFileOutput(FILE_NAME, MODE_APPEND); // Try to open the file

        } catch (FileNotFoundException e) {

            String warning =  R.string.write_error + e.toString();

            txtDisplayScore.setText(warning); // Return an error if it can't
            return;
        }

        Score writeScore = new Score(arg_name, arg_score); // Make a new Score object

        PrintWriter writer = new PrintWriter(fos);

        writer.println(writeScore.toFile()); // Write the score by parsing it with the toFile method in Score Class

        writer.close(); // Close the file that was being written to

    }

    // Method is called when the App starts, it reads a file that has high scores in it
    public void read() {

        // Declare the INPUT stream
        FileInputStream fis;

        // Get the FileInputStream from the Context method
        try {
            fis = openFileInput(FILE_NAME);
        } catch (FileNotFoundException e) {
            high_scores.add(new Score(null, 0));
            return;
        }

        Scanner scanner;

        // Wrap a Scanner around FileInputStream for
        // convenience of nextLine since reading text.
        scanner = new Scanner(fis);

        // Keep reading the file as long as there's data
        while (scanner.hasNextLine()) {

            String text = scanner.nextLine();

            Score readScore = new Score(null, 0);

            high_scores.add(readScore.Score(text));
        }

        // Close the Scanner which will also close fis
        scanner.close();

        }
}
