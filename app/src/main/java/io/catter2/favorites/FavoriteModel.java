package io.catter2.favorites;

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

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FavoriteModel) {
            FavoriteModel f = (FavoriteModel) obj;
            return url.equals(f.getUrl()) && timeAdded == f.timeAdded;
        }
        return false;
    }
}
