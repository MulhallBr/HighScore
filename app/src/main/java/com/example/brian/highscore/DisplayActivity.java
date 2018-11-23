package com.example.brian.highscore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.brian.highscore.model.Score;

import java.util.ArrayList;

public class DisplayActivity extends Activity {

    public RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        loadScores();
    }

    // Method is called as soon as the DisplayActivity starts
    // Takes the array passed in the parcelable and displays the high scores in descending order
    public void loadScores() {

        ArrayList<Score> inflateArray = this.getIntent().getParcelableArrayListExtra("score");  // Inflates the parcelable (ArrayList)

        mRecyclerView = findViewById(R.id.recycler_view);

        // This is just a Horizontal Line Decoration
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // Create a LinearLayout Manager and set it to Vertical Layout
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Assign the Layout Manager to our RecyclerView
        mRecyclerView.setLayoutManager(manager);

        // Create the adapter and assign it to the RecyclerView
        ScoreAdapter adapter = new ScoreAdapter(inflateArray);
        mRecyclerView.setAdapter(adapter);

        // Clear the recycler view if there is no scores to display, but someone can still enter a score of zero
        if (inflateArray.get(0).getScore() == 0 && inflateArray.get(0).getName() == null) {
            mRecyclerView.setAdapter(null);
        }

    }


}
