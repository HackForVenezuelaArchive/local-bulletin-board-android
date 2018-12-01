package wpi.kgcm.hackforvenezuela;

import java.util.Date;

/**
 * Created by mac on 12/1/18.
 */

public class Post {

    public String author;
    public String title;
    public String body;
    public Long timestamp;
    public Double lat;
    public Double lng;

    public Post(String title, String author, String body, Long timestamp, Double lat, Double lng) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
        this.lat = lat;
        this.lng = lng;
    }

    public Post() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
