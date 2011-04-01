package com.rhymestore.android;

import android.accounts.Account;

public interface AccountAuthenticationFacade
{
    public boolean checkExisitingAccount(final Account account);

    public abstract Account createAccount(final Account account);
}
