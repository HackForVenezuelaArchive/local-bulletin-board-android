package wpi.kgcm.hackforvenezuela;

/**
 * Created by mac on 12/1/18.
 */

public class Post {

    public String author;
    public String title;

    public Post() {
        this.author = " ";
        this.title = " ";
    }

    public Post(String author, String title) {
        this.author = author;
        this.title = title;
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
