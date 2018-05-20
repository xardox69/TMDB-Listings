package com.dubizzle.app.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Asset on the detail screen
 * Created by usman on 8/9/17.
 */
@JsonIgnoreProperties(ignoreUnknown=true)


/**
 * Class used to represent a detailed asset
 *
 */
public class DetailedAsset implements Parcelable {
    private String title;
    private String name;
    private String tagline;
    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("first_air_date")
    private String firstAirDate;
    private int runtime;
    private String overview;

    public DetailedAsset(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    @JsonProperty("backdrop_path")
    private String backdropPath;


    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getOverview() {
        return overview;
    }


    public String getName() {
        return name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.tagline);
        dest.writeString(this.releaseDate);
        dest.writeString(this.firstAirDate);
        dest.writeInt(this.runtime);
        dest.writeString(this.overview);
        dest.writeString(this.backdropPath);
    }

    public DetailedAsset() {
    }

    protected DetailedAsset(Parcel in) {
        this.title = in.readString();
        this.name = in.readString();
        this.tagline = in.readString();
        this.releaseDate = in.readString();
        this.firstAirDate = in.readString();
        this.runtime = in.readInt();
        this.overview = in.readString();
        this.backdropPath = in.readString();
    }

    public static final Parcelable.Creator<DetailedAsset> CREATOR = new Parcelable.Creator<DetailedAsset>() {
        @Override
        public DetailedAsset createFromParcel(Parcel source) {
            return new DetailedAsset(source);
        }

        @Override
        public DetailedAsset[] newArray(int size) {
            return new DetailedAsset[size];
        }
    };
}
