package no.dervis;

import no.dervis.gts.TwitterAPI;
import no.dervis.gts.TwitterStats;
import twitter4j.TwitterFactory;

import static no.dervis.gts.configuration.AuthConfiguration.configureWithAccessToken;

public class Main {

    public static void main(String[] args) {

        TwitterAPI api = new TwitterAPI(new TwitterFactory(configureWithAccessToken()).getInstance());
        TwitterStats stats = new TwitterStats(api);

        System.out.println(stats.showUser("dervis_m"));
    }

}
