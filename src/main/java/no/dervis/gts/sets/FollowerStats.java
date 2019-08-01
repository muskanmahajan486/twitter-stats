package no.dervis.gts.sets;

import java.util.List;
import java.util.stream.Collectors;

public class FollowerStats {

    /**
     * Returns the relative complement of two lists of users.
     *
     * @param oldSet The existing list of users.
     * @param newSet The new list of users.
     * @return The list of users that are only in the old list.
     */
    public static <T> List<T> getDifference(List<T> oldSet, List<T> newSet) {
        return oldSet.stream()
                .filter(newSet::contains)
                .collect(Collectors.toList());
    }

}
