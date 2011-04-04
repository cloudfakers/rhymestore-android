package com.rhymestore.android.authenticator;

import android.accounts.Account;
import android.content.Context;

import com.rhymestore.android.accountsmanager.AccountsManager;

public class TwitterAuthentication extends AccountsManager
{
    private static final String TWITTER_CONSUMER_KEY = "lVRLHZKj8WRbkeTl9nFrNA";

    private static final String TWITTER_CONSUMER_SECRET =
        "aenbplQ56zlkXYPPz1ixLQGeHPPDgPK0FGskHrjLU";

    private static final String TWITTER_ACCOUNTMANAGER_TYPE = "com.twitter";

    public TwitterAuthentication(final Context context)
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
