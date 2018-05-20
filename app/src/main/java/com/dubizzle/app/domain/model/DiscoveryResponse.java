package com.dubizzle.app.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Resposne of the discovery API
 * Created by usman on 8/8/17.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DiscoveryResponse {

    private int page;
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("results")
    private ArrayList<TmdbAsset> assets;


    public DiscoveryResponse() {
        page = 0;
        totalPages = 0;
        totalResults = 0;
        assets = new ArrayList<>();
    }

    public DiscoveryResponse(int page, int totalResults, int totalPages, ArrayList<TmdbAsset> assets) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.assets = assets;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<TmdbAsset> getAssets() {
        return assets;
    }

    @Override
    public String toString() {
        return "DiscoveryResponse{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", assets=" + assets +
                '}';
    }
}
