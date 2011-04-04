package com.rhymestore.android.accountsmanager;

import android.accounts.Account;
import android.content.Context;


public class TwitterAccountManager extends AccountsManager
{
    private static final String TWITTER_CONSUMER_KEY = "lVRLHZKj8WRbkeTl9nFrNA";

    private static final String TWITTER_CONSUMER_SECRET =
        "aenbplQ56zlkXYPPz1ixLQGeHPPDgPK0FGskHrjLU";

    private static final String TWITTER_ACCOUNTMANAGER_TYPE = "com.twitter.android.auth.login";

    public TwitterAccountManager(final Context context)
    {
        super(context, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, TWITTER_ACCOUNTMANAGER_TYPE);
    }

    @Override
    public Account createAccount(final Account account)
    {
        // getAccountManager().addAccountExplicitly(account, password, userdata);
        // TODO Auto-generated method stub
        return null;
    }
}
