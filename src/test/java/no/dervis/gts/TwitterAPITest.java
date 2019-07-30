package no.dervis.gts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import twitter4j.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TwitterAPITest {

    @BeforeAll
    public static void setup() {

    }

    @Test
    void showUser() throws TwitterException {
        User user = makeTestUser(1);

        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.showUser(anyString())).thenReturn(user);

        TwitterStats stats = new TwitterStats(new TwitterAPI(twitterMock));

        assertEquals("test1", stats.showUser("test").getScreenName());
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

        Function<AtomicInteger, Stream<User>> func = i -> Stream.generate(() -> makeTestUser(i.getAndIncrement())).limit(2);

        PagableResponseList page1 = mock(PagableResponseList.class);
        when(page1.getNextCursor()).thenReturn(1L);
        when(page1.stream()).then(invocationOnMock -> func.apply(new AtomicInteger(1)));

        PagableResponseList page2 = mock(PagableResponseList.class);
        when(page2.getNextCursor()).thenReturn(0L);
        when(page2.stream()).then(invocationOnMock -> func.apply(new AtomicInteger(3)));

        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.getFollowersList(anyString(), eq(-1L), anyInt())).thenReturn(page1);
        when(twitterMock.getFollowersList(anyString(), eq(1L), anyInt())).thenReturn(page2);

        TwitterStats stats = new TwitterStats(new TwitterAPI(twitterMock));

        assertEquals(4, stats.getFollowers("test").size());

        assertEquals(Arrays.asList("test1", "test2", "test3", "test4"), stats.getFollowersList(""));
    }

    private List<User> makeTestUsers(int n) {
        return IntStream.range(0, n)
                .mapToObj(this::makeTestUser)
                .collect(Collectors.toList());
    }

    private User makeTestUser(int n) {
        try {
            return TwitterObjectFactory.createUser("{\"screen_name\":\"test" + n + "\"" + "}");
        } catch (TwitterException e) {
            throw new RuntimeException("Coulndt create user.", e);
        }
    }


}