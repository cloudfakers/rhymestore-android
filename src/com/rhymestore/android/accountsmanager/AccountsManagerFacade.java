package com.rhymestore.android.accountsmanager;

import android.accounts.Account;

public interface AccountsManagerFacade
{
    public abstract Account addAccount(final Account account);

    public abstract boolean tryAuthentication();
}
