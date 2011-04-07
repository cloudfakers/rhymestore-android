package com.rhymestore.android.accountsmanager;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class TwitterAccountManager extends AccountsManager
{
    private static final String TWITTER_CONSUMER_KEY = "lVRLHZKj8WRbkeTl9nFrNA";

    private static final String TWITTER_CONSUMER_SECRET =
        "aenbplQ56zlkXYPPz1ixLQGeHPPDgPK0FGskHrjLU";

    private static final String TWITTER_ACCOUNTMANAGER_TYPE = "com.twitter.android.auth.login";

    private static final String TWITTER_CALLBACKURL = "rhymestore://twitter";

    private static TwitterAccountManager instance;

    private RequestToken requestToken;

    private static Context context;

    public TwitterAccountManager(final Context context)
    {
        super(context, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, TWITTER_ACCOUNTMANAGER_TYPE);
        this.context = context;
    }

    public static TwitterAccountManager getInstance()
    {
        if (instance == null)
        {
            instance = new TwitterAccountManager(context);
        }

        return instance;
    }

    @Override
    public Account addAccount(final Account account)
    {
        // getAccountManager().addAccountExplicitly(account, password, userdata);
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void requestAuthentication() throws Exception
    {
        Twitter twitter = new TwitterFactory().getInstance();

        try
        {
            twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
            requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACKURL);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken
                .getAuthenticationURL())));
        }
        catch (IllegalStateException ex)
        {
            // Access token is already available, or consumer key/secret is not set.
            if (twitter.getAuthorization().isEnabled() == false)
            {
                throw new Exception("Error: OAuth consumer key/secret is not set.");
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error: " + ex.getMessage());
        }
    }

    @Override
    public Boolean tryAuthentication(final String oAuthVerifier) throws Exception
    {
        try
        {
            Twitter twitter2 = new TwitterFactory().getInstance();
            twitter2.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
            AccessToken accessToken = twitter2.getOAuthAccessToken(requestToken, oAuthVerifier);
            String token = accessToken.getToken();
            String secret = accessToken.getTokenSecret();

            Twitter t = new TwitterFactory().getInstance();
            t.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
            t.setOAuthAccessToken(new AccessToken(token, secret));

            return true;
        }
        catch (TwitterException ex)
        {
            throw new Exception("Error: " + ex.getMessage());
        }
    }
}
