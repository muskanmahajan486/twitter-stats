package no.dervis.gts;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.User;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class TwitterAPI {

    private final Twitter twitter;

    private int maxPageSize = 200;

    public TwitterAPI(Twitter twitter) {
        this.twitter = twitter;
    }

    User showUser(final String screenName) {
        return callTwitterAPI(() -> twitter.showUser(screenName), "Cannot find user.");
    }

    List<User> getFollowers(final String screenName) {
        List<PagableResponseList<User>> list = followers(screenName, -1, new LinkedList<>());

        return list
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Recursive function that builds up a list of followers.
     *
     * @param screenName Screen name of person to look up
     * @param cursor Index of current page
     * @param list The list of pages that contain the followers
     * @return The complete list of pages of followers
     */
    private List<PagableResponseList<User>> followers(final String screenName, long cursor,
                                                      List<PagableResponseList<User>> list) {

        if (cursor == 0) {
            return list;
        }

        PagableResponseList<User> page = callTwitterAPI(
                () -> twitter.getFollowersList(screenName, cursor, maxPageSize),
                "Cannot get followers list");

        list.add(page);

        return followers(screenName, page.getNextCursor(), list);
    }


    /**
     * Handles checked exceptions from a functions.
     * @param function Any callable function that might throw an exception
     * @param msg The log message to supply with the stacktrace.
     * @param <R> The generic type of the return value.
     * @return
     */
    private <R> R callTwitterAPI(final Callable<R> function, final String msg) {
        try {
            return function.call();
        } catch (Exception e) {
            throw new RuntimeException(msg, e);
        }
    }

    public TwitterAPI setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
        return this;
    }

}
