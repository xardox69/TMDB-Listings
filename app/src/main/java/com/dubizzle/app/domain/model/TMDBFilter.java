package com.dubizzle.app.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TMDBFilter implements Parcelable {

    int minYear;
    int maxYear;

    public TMDBFilter(int minYear, int maxYear) {
        this.minYear = minYear;
        this.maxYear = maxYear;
    }


    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.minYear);
        dest.writeInt(this.maxYear);
    }



    protected TMDBFilter(Parcel in) {
        this.minYear = in.readInt();
        this.maxYear = in.readInt();
    }

    public static final Parcelable.Creator<TMDBFilter> CREATOR = new Parcelable.Creator<TMDBFilter>() {
        @Override
        public TMDBFilter createFromParcel(Parcel source) {
            return new TMDBFilter(source);
        }

        @Override
        public TMDBFilter[] newArray(int size) {
            return new TMDBFilter[size];
        }
    };

    @Override
    public String toString() {
        return "TMDBFilter{" +
                "minYear=" + minYear +
                ", maxYear=" + maxYear +
                '}';
    }
}
