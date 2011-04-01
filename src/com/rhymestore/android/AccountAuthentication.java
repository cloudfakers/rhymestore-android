package com.rhymestore.android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public abstract class AccountAuthentication implements AccountAuthenticationFacade
{
    private String consumerKey;

    private String consumerSecret;

    private String accountType;

    private Account[] accountsList;

    private AccountManager accountManager;

    private Context context;

    private Account requiredAccount;

    public AccountAuthentication(final Context context, final String consumerKey,
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
        setAccountsList(getAccountManager().getAccounts());

        // Check if the requestedAccount exists and create it if does not exist
        Account[] requestedAccount = getAccountManager().getAccountsByType(getAccountType());

        if (checkExisitingAccount(requestedAccount[0]) == false)
        {
            // Create the account
            createAccount(requestedAccount[0]);
        }

    }

    public boolean checkExisitingAccount(final Account account)
    {
        Account[] accountsList = getAccountsList();

        for (Account currentAccount : accountsList)
        {
            if (currentAccount == account)
            {
                return true;
            }
        }

        return false;
    }

    public abstract Account createAccount(final Account account);

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
