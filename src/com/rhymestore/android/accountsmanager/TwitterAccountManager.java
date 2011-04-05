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
import android.util.Log;

public class TwitterAccountManager extends AccountsManager
{
    private static final String TWITTER_CONSUMER_KEY = "lVRLHZKj8WRbkeTl9nFrNA";

    private static final String TWITTER_CONSUMER_SECRET =
        "aenbplQ56zlkXYPPz1ixLQGeHPPDgPK0FGskHrjLU";

    private static final String TWITTER_ACCOUNTMANAGER_TYPE = "com.twitter.android.auth.login";

    private static final String TWITTER_CALLBACKURL = "rhymestore://HomeActivity";

    private Twitter twitter = new TwitterFactory().getInstance();

    private RequestToken requestToken;

    private Context context;

    public TwitterAccountManager(final Context context)
    {
        super(context, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, TWITTER_ACCOUNTMANAGER_TYPE);
        this.context = context;

        twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
    }

    @Override
    public Account addAccount(final Account account)
    {
        // getAccountManager().addAccountExplicitly(account, password, userdata);
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean requestAuthentication() throws Exception
    {
        try
        {
            requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACKURL);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken
                .getAuthenticationURL())));

            Log.d("RHYME", "KIKOO: asking API");

            return true;
        }
        catch (IllegalStateException ex)
        {
            // access token is already available, or consumer key/secret is not set.
            if (twitter.getAuthorization().isEnabled() == false)
            {
                Log.d("RHYME", "OAuth consumer key/secret is not set.");
                throw new Exception("Error: OAuth consumer key/secret is not set.");
            }

            return false;
        }
        catch (Exception ex)
        {
            Log.d("RHYME", "Exception: " + ex.getMessage());
            throw new Exception("Exception: " + ex.getMessage());
        }
    }

    @Override
    public void tryAuthentication(final String oAuthVerifier) throws Exception
    {
        try
        {
            requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACKURL);
            AccessToken acessToken = twitter.getOAuthAccessToken(requestToken, oAuthVerifier);
            String token = acessToken.getToken();
            String secret = acessToken.getTokenSecret();

            twitter.setOAuthAccessToken(new AccessToken(token, secret));
            Log.d("RHYME", "KIKOO: logged");
        }
        catch (TwitterException ex)
        {
            throw new Exception("Error: " + ex.getMessage());
        }
    }
}
