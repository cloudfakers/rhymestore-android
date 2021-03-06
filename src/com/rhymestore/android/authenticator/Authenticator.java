package com.rhymestore.android.authenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

class Authenticator extends AbstractAccountAuthenticator
{
    private final Context context;

    public Authenticator(final Context context)
    {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle addAccount(final AccountAuthenticatorResponse response, final String accountType,
        final String authTokenType, final String[] requiredFeatures, final Bundle options)
        throws NetworkErrorException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle confirmCredentials(final AccountAuthenticatorResponse response,
        final Account account, final Bundle options) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public Bundle editProperties(final AccountAuthenticatorResponse response,
        final String accountType)
    {
        return null;
    }

    @Override
    public Bundle getAuthToken(final AccountAuthenticatorResponse response, final Account account,
        final String authTokenType, final Bundle options) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public String getAuthTokenLabel(final String authTokenType)
    {
        return null;
    }

    @Override
    public Bundle hasFeatures(final AccountAuthenticatorResponse response, final Account account,
        final String[] features) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public Bundle updateCredentials(final AccountAuthenticatorResponse response,
        final Account account, final String authTokenType, final Bundle options)
        throws NetworkErrorException
    {
        return null;
    }
}
