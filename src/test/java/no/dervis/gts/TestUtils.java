package no.dervis.gts;

import no.dervis.gts.database.SnapShot;
import twitter4j.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {

    public static List<SnapShot> makeSnapShots(int n, int j) {

        List<SnapShot> list = new LinkedList<>();
        range(0, n).forEach(i -> {
            final SnapShot snapShot = new SnapShot(makeTestUsers(j));
            snapShot.setDateTime(snapShot.getDateTime().minusDays(i));
            list.add(snapShot);
        });

        return list;
    }

    @SuppressWarnings("unchecked")
    public static Twitter setupPagableResponsListMock() throws TwitterException {
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

        return twitterMock;
    }

    public static List<User> makeTestUsers(int n) {
        return range(0, n)
                .mapToObj(TestUtils::makeTestUser)
                .collect(Collectors.toList());
    }

    public static User makeTestUser(int n) {
        try {
            return TwitterObjectFactory.createUser("{\"screen_name\":\"test" + n + "\"" + "}");
        } catch (TwitterException e) {
            throw new RuntimeException("Coulndt create user.", e);
        }
    }

}
