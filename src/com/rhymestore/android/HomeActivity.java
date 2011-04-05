package com.rhymestore.android;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rhymestore.android.accountsmanager.TwitterAccountManager;
import com.rhymestore.android.rhymes.ListRhymesActivity;

public class HomeActivity extends Activity
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        TwitterAccountManager twitterAuth = new TwitterAccountManager(this);
        Account[] accountsList = twitterAuth.getAccountsList();
        shortAlert("Nombre : " + accountsList.length);

        for (Account currentAccount : accountsList)
        {
            Log.d("Rhymes", "KIKOO: " + currentAccount.name);
        }

        // Account account = new Account(username, getString(R.string.ACCOUNT_TYPE)));
        // twitterAuth.createAccount(account);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        if (item.getItemId() == R.id.menu_list)
        {
            startActivity(new Intent(HomeActivity.this, ListRhymesActivity.class));
            return true;
        }

        return false;
    }

    private void shortAlert(final String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
