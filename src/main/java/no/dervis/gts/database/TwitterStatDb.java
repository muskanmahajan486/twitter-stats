package no.dervis.gts.database;

import twitter4j.User;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TwitterStatDb implements Serializable {

    private List<TwitterSnapShot> data;

    public TwitterStatDb() {
        this.data = new LinkedList<>();
    }

    public TwitterStatDb addSnapShot(TwitterSnapShot twitterSnapShot) {
        data.add(twitterSnapShot);
        return this;
    }

    public TwitterStatDb addFollowers(List<User> list) {
        data.add(new TwitterSnapShot(list));
        return this;
    }

    public List<TwitterSnapShot> getData() {
        return data;
    }

    public void setData(List<TwitterSnapShot> data) {
        this.data = data;
    }
}
