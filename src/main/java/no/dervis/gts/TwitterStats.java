package no.dervis.gts;

import twitter4j.User;

import java.util.List;
import java.util.stream.Collectors;

public class TwitterStats {

    private final TwitterAPI api;

    public TwitterStats(TwitterAPI api) {
        this.api = api;
    }

    public User showUser(String screenName) {
        return api.showUser(screenName);
    }

    public List<User> getFollowerUserList(String screenName) {
        return api.getFollowers(screenName);
    }

    public List<String> getFollowerScreenNames(String screenName) {
        return getFollowerUserList(screenName)
                .stream()
                .map(User::getScreenName)
                .collect(Collectors.toList());
    }
}
