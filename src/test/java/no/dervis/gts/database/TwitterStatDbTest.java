package no.dervis.gts.database;

import no.dervis.gts.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TwitterStatDbTest {

    @Test
    void sortData() {
        int numDays = 5;
        final List<SnapShot> data = TestUtils.makeSnapShots(numDays, numDays);
        Collections.shuffle(data);

        LocalDateTime now = LocalDateTime.now();
        while(now.getDayOfMonth() == data.get(0).getDateTime().getDayOfMonth()) Collections.shuffle(data);

        System.out.println("Before sort:");
        System.out.println(data);

        assertNotEquals(now.getDayOfMonth(), data.get(0).getDateTime().getDayOfMonth());

        TwitterStatDb db = new TwitterStatDb(data);

        System.out.println("After sort:");
        System.out.println(db.sortData().getData());

        assertEquals(now.minusDays(numDays-1).getDayOfMonth(), db.first().orElseThrow().getDateTime().getDayOfMonth());
        assertEquals(now.getDayOfMonth(), db.last().orElseThrow().getDateTime().getDayOfMonth());
    }

    @Test
    void reverseOrder() {
        int numDays = 5;
        final List<SnapShot> data = TestUtils.makeSnapShots(numDays, numDays);

        TwitterStatDb db = new TwitterStatDb(data);

        System.out.println("After sort and reverseOrder:");
        System.out.println(db.sortData().reverseOrder().getData());

        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.getDayOfMonth(), db.first().orElseThrow().getDateTime().getDayOfMonth());
        assertEquals(now.minusDays(numDays-1).getDayOfMonth(), db.last().orElseThrow().getDateTime().getDayOfMonth());
    }

}