package com.rhymestore.android.accountsmanager;

import android.accounts.Account;

public interface AccountsManagerFacade
{
    public abstract Account addAccount(final Account account);

    public abstract Boolean requestAuthentication() throws Exception;

    public abstract void tryAuthentication(String oAuthVerifier) throws Exception;
}
