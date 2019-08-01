package no.dervis;

import no.dervis.gts.TwitterAPI;
import no.dervis.gts.TwitterStats;
import no.dervis.gts.database.DataIO;
import no.dervis.gts.database.TwitterStatDb;
import twitter4j.TwitterFactory;

import java.nio.file.Paths;

import static no.dervis.gts.configuration.AuthConfiguration.configureWithAccessToken;

public class Main {

    public static void main(String[] args) {

        TwitterAPI api = new TwitterAPI(new TwitterFactory(configureWithAccessToken()).getInstance());
        TwitterStats stats = new TwitterStats(api);

        final String filePath = Paths.get(DataIO.dataFolder, DataIO.defaultFilename).normalize().toString();
        System.out.println(filePath);

        final TwitterStatDb db = DataIO.readFile(TwitterStatDb.class,
                filePath)
                .sortData()
                .reverseOrder();
        System.out.println(db);

        /*DataIO.saveAsFile(
                new TwitterStatDb()
                        .addFollowers(stats.getFollowers("abcd"))
        );*/

    }

}
