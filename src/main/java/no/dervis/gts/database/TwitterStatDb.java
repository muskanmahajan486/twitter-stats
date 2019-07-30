package no.dervis.gts.database;

import twitter4j.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class TwitterStatDb implements Serializable {

    private List<TwitterSnapShot> data;

    public TwitterStatDb() {
        this.data = new LinkedList<>();
    }

    public void saveAsFile() {
        try {
            FileOutputStream fout = new FileOutputStream(Paths.get("./twitter.stats.data").normalize().toString());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TwitterStatDb addSnapShot(TwitterSnapShot twitterSnapShot) {
        data.add(twitterSnapShot);
        return this;
    }

    public TwitterStatDb addFollowers(List<User> list) {
        data.add(new TwitterSnapShot(list));
        return this;
    }

    public List<TwitterSnapShot> readData() {

        return null;
    }

    public List<TwitterSnapShot> getData() {
        return data;
    }

    public void setData(List<TwitterSnapShot> data) {
        this.data = data;
    }
}
