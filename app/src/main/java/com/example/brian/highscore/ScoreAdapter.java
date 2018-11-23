package com.example.brian.highscore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brian.highscore.model.Score;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.MyViewHolder> {

    // Hold the data for the adapter
    private ArrayList<Score> mScores;

    // A constructor that accepts the Array of Scores
    ScoreAdapter(ArrayList<Score> scores) {
        mScores = scores;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        // Holds a reference to the TextView
        private TextView mTxtScores;
        private TextView mTxtName;


        MyViewHolder(View itemView) {
            super(itemView);
            mTxtScores = itemView.findViewById(R.id.recycler_score_value);
            mTxtName = itemView.findViewById(R.id.recycler_name);
        }
    }

    /**
     * Returns a newly created MyViewHolder. Note that with the recycling of views,
     * this will no necessarily be called for all elements to be displayed,
     * especially for larger lists.
     * @param parent The parent viewgroup (the RecyclerView)
     * @param viewType The view type of the new View
     */

        @Override
        public ScoreAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            // Get the TextView we created
            //LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_display, parent, false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_display, parent, false);

            // Create an instance of MyViewHolder
            MyViewHolder viewHolder;
            viewHolder = new MyViewHolder(view);

            return viewHolder;
    }

    /**
     * This will be called by the LayoutManager when it needs a new View to display
     * in the RecyclerView.
     * @param holder A holder that is being re-used
     * @param position The position in the RecyclerView
     */

        @Override
        public void onBindViewHolder (ScoreAdapter.MyViewHolder holder, int position) {

            Score score = mScores.get(position);

            // Simply set the text to the desired Score
            Integer temp = score.getScore();
            holder.mTxtScores.setText(temp.toString());
            holder.mTxtName.setText(score.getName());
    }

    /**
     * This method can be called often and should return quickly
     * @return The total number of elements to be displayed
     */

        @Override
        public int getItemCount() {
            return mScores.size();
    }
}
