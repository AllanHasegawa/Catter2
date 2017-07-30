package io.catter2;

public class FavoriteModel {
    private long timeAdded;
    private String url;

    public FavoriteModel(long timeAdded, String url) {
        this.timeAdded = timeAdded;
        this.url = url;
    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public String getUrl() {
        return url;
    }
}
