package com.rhymestore.android.accountsmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public abstract class AccountsManager implements AccountsManagerFacade
{
    private String consumerKey;

    private String consumerSecret;

    private String accountType;

    private Account[] accountsList;

    private AccountManager accountManager;

    private Context context;

    private Account requiredAccount;

    public AccountsManager(final Context context, final String consumerKey,
        final String consumerSecret, final String accountType)
    {
        // Get and save the parameters
        setContext(context);
        setConsumerKey(consumerKey);
        setConsumerSecret(consumerSecret);
        setAccountType(accountType);

        // Initialize the AccountManager
        setAccountManager(AccountManager.get(getContext()));

        // Get the list of all existing accounts in the AccountManager
        setAccountsList(getAccountManager().getAccountsByType(null));
    }

    public abstract Account createAccount(final Account account);

    public boolean checkExisitingAccount()
    {
        Account[] accountsList = getAccountsList();
        Account[] requestedAccount = getAccountManager().getAccountsByType(getAccountType());

        for (Account currentAccount : accountsList)
        {
            if (currentAccount == requestedAccount[0])
            {
                return true;
            }
        }

        return false;
    }

    public void setAccountsList(final Account[] accountsList)
    {
        this.accountsList = accountsList;
    }

    public Account[] getAccountsList()
    {
        return accountsList;
    }

    public void setAccountManager(final AccountManager accountManager)
    {
        this.accountManager = accountManager;
    }

    public AccountManager getAccountManager()
    {
        return accountManager;
    }

    public void setContext(final Context context)
    {
        this.context = context;
    }

    public Context getContext()
    {
        return context;
    }

    public void setRequiredAccount(final Account requiredAccount)
    {
        this.requiredAccount = requiredAccount;
    }

    public Account getRequiredAccount()
    {
        return requiredAccount;
    }

    public void setConsumerKey(final String consumerKey)
    {
        this.consumerKey = consumerKey;
    }

    public String getConsumerKey()
    {
        return consumerKey;
    }

    public void setConsumerSecret(final String consumerSecret)
    {
        this.consumerSecret = consumerSecret;
    }

    public String getConsumerSecret()
    {
        return consumerSecret;
    }

    public void setAccountType(final String accountType)
    {
        this.accountType = accountType;
    }

    public String getAccountType()
    {
        return accountType;
    }
}
