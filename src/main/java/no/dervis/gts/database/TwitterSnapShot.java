package no.dervis.gts.database;

import twitter4j.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class TwitterSnapShot implements Serializable {

    private List<User> followers;

    private LocalDateTime dateTime;

    public TwitterSnapShot(List<User> followers) {
        this.followers = followers;
        this.dateTime = LocalDateTime.now();
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
