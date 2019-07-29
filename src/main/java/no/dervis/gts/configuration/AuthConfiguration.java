package no.dervis.gts.configuration;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static no.dervis.gts.properties.Env.requireSystemProperty;

public class AuthConfiguration {

    public static Configuration configureWithAccessToken() {
        return new ConfigurationBuilder()
                .setDebugEnabled(false)
                .setGZIPEnabled(true)
                .setOAuthConsumerKey(requireSystemProperty("CONSUMER_KEY"))
                .setOAuthConsumerSecret(requireSystemProperty("CONSUMER_SECRET"))
                .setOAuthAccessToken(requireSystemProperty("ACCESS_TOKEN"))
                .setOAuthAccessTokenSecret(requireSystemProperty("ACCESS_SECRET"))
                .build();
    }

}
