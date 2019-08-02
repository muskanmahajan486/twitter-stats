package no.dervis.gts.database;

import twitter4j.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SnapShot implements Serializable, Comparable<SnapShot> {

    private static final long serialVersionUID = 5427835547862392347L;

    private List<User> followers;

    private LocalDateTime dateTime;

    public SnapShot() {
        this(new LinkedList<>());
    }

    public SnapShot(List<User> followers) {
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

    public List<String> getFollowerScreenNames() {
        return followers.stream().map(User::getScreenName).collect(Collectors.toList());
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int compareTo(SnapShot o) {
        return this.getDateTime().compareTo(o.getDateTime());
    }

    @Override
    public String toString() {
        int trunk = 5;
        final List<String> screenNames = getFollowerScreenNames();

        return "SnapShot{" +
                String.format("followers (%s) = %s", screenNames.size(),
                        (screenNames.size() > trunk ? screenNames.subList(0, trunk) + " plus " + (screenNames.size() - trunk) + " more" : screenNames)) +
                ", dateTime=" + dateTime.format(DateTimeFormatter.ofPattern(DataIO.dateTimePattern)) +
                "}\n";
    }
}
