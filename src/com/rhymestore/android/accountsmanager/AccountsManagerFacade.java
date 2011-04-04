package com.rhymestore.android.accountsmanager;

import android.accounts.Account;

public interface AccountsManagerFacade
{
    public abstract Account createAccount(final Account account);
}
