package com.example.brian.highscore.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static java.lang.String.format;

public class Score implements Parcelable, Comparable<Score> {

    private int score;
    private String name;

    // Constructor for the Score Class
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Score Score(String text) {

        String[] separated = text.split(":");

        String currentName = separated[0];
        int currentScore = Integer.parseInt(separated[1]);

        return new Score(currentName, currentScore);

    }

    public String toFile() {
        // Create a comma-delimited string for the file
        return format("%s:%s", name, score);
    }

    // Get the score attribute
    public int getScore() {
        return score;
    }

    //Get the name attribute
    public String getName() {
        return name;
    }

    // Not Needed
    public void setName(String name) {
        this.name = name;
    }

    // Not Needed
    public void setScore(int score) {
        this.score = score;
    }

    private Score(Parcel in) {
        score = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {

        return 0;
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(score);
        parcel.writeString(name);
    }

    @Override
    public int compareTo(@NonNull Score score) {
        int compareScore = score.getScore();
        return compareScore - this.score;
    }
}
