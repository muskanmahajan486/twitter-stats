package no.dervis.gts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TwitterAPITest {

    @Test
    void showUser() throws TwitterException {
        User user = TestUtils.makeTestUser(1);

        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.showUser(anyString())).thenReturn(user);

        TwitterStats stats = new TwitterStats(new TwitterAPI(twitterMock));

        User test = stats.showUser("test");

        assertNotNull(test);
        assertEquals("test1", test.getScreenName());
    }

    @Test
    void showUserShouldThrowException() throws TwitterException {
        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.showUser(anyString())).thenThrow(new TwitterException(""));

        TwitterStats stats = new TwitterStats(new TwitterAPI(twitterMock));

        Assertions.assertThrows(RuntimeException.class, () -> {
            stats.showUser("test");
        });
    }

    @Test
    void getFollowers() throws TwitterException {

        TwitterStats stats = new TwitterStats(new TwitterAPI(TestUtils.setupPagableResponsListMock()));

        List<User> followers = stats.getFollowerUserList("test");

        assertNotNull(followers);
        assertEquals(4, followers.size());
    }

    @Test
    void getFollowersList() throws TwitterException {

        TwitterStats stats = new TwitterStats(new TwitterAPI(TestUtils.setupPagableResponsListMock()));

        List<String> followersList = stats.getFollowerScreenNames("");
        assertNotNull(followersList);
        assertEquals(Arrays.asList("test1", "test2", "test3", "test4"), followersList);
    }

}