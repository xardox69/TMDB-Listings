package com.dubizzle.app.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A class to represnt an asset in grid
 * Created by usman on 8/8/17.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class TmdbAsset implements Parcelable {

    private String name;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("vote_average")
    private int rating;
    private String id;




    public TmdbAsset() {
    }

    public TmdbAsset(String name, String posterPath, int rating, String id) {
        this.name = name;
        this.posterPath = posterPath;
        this.rating = rating;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.posterPath);
        dest.writeInt(this.rating);
        dest.writeString(this.id);
    }

    protected TmdbAsset(Parcel in) {
        this.name = in.readString();
        this.posterPath = in.readString();
        this.rating = in.readInt();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<TmdbAsset> CREATOR = new Parcelable.Creator<TmdbAsset>() {
        @Override
        public TmdbAsset createFromParcel(Parcel source) {
            return new TmdbAsset(source);
        }

        @Override
        public TmdbAsset[] newArray(int size) {
            return new TmdbAsset[size];
        }
    };

    @Override
    public String toString() {
        return "TmdbAsset{" +
                "name='" + name + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", rating=" + rating +
                ", id='" + id + '\'' +
                '}';
    }
}
