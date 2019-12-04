package no.dervis.gts.database;

import java.util.LinkedList;
import java.util.List;

public class SnapShotList {

    private final String screenName;

    private final List<SnapShot> snapShots = new LinkedList<>();

    public SnapShotList(String screenName) {
        this.screenName = screenName;
    }

    public SnapShotList addSnapShot(SnapShot snapShot) {
        snapShots.add(snapShot);
        return this;
    }
}
