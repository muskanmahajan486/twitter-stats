package no.dervis;

import no.dervis.gts.TwitterAPI;
import no.dervis.gts.TwitterStats;
import no.dervis.gts.database.DataIO;
import no.dervis.gts.database.SnapShot;
import no.dervis.gts.database.TwitterStatDb;
import no.dervis.gts.sets.FollowerStats;
import twitter4j.TwitterFactory;

import java.nio.file.Paths;
import java.util.List;

import static java.lang.String.format;
import static no.dervis.gts.configuration.AuthConfiguration.configureWithAccessToken;

public class Main {

    public static void main(String[] args) {

        TwitterAPI api = new TwitterAPI(new TwitterFactory(configureWithAccessToken()).getInstance());
        TwitterStats stats = new TwitterStats(api);

        final String filePath = Paths.get(DataIO.dataFolder, DataIO.defaultFilename).normalize().toString();
        System.out.println(filePath);

        TwitterStatDb db = null;

        try {
            // read data from disk
            db = DataIO.readFile(TwitterStatDb.class, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // fetch new list
        final SnapShot snapShot = new SnapShot(stats.getFollowerUserList("abcd"));

        // fetch the previous list
        final List<String> previous = db.sortData().reverseOrder().first().orElse(new SnapShot()).getFollowerScreenNames();
        final List<String> lastest = snapShot.getFollowerScreenNames();

        // collect stats
        final List<String> lostFollowers = FollowerStats.getDifference(previous, lastest);
        final List<String> gainedFollowers = FollowerStats.getDifference(lastest, previous);

        System.out.println(format("You lost %s followers", lostFollowers.size()));
        System.out.println(lostFollowers);

        System.out.println(format("You gained %s followers", gainedFollowers.size()));
        System.out.println(gainedFollowers);

        DataIO.saveAsFile(db.addSnapShot(snapShot));
    }

}
