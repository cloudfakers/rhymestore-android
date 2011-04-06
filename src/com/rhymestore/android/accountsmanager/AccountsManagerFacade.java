package com.rhymestore.android.accountsmanager;

import android.accounts.Account;

public interface AccountsManagerFacade
{
    public abstract Account addAccount(final Account account);

    public abstract void requestAuthentication() throws Exception;

    public abstract Boolean tryAuthentication(String oAuthVerifier) throws Exception;
}
