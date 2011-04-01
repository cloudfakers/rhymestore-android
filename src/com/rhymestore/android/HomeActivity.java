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

public class HomeActivity extends Activity
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        AccountAuthentication twitterAuth = new AccountAuthentication(this);
        Account[] accountsList = twitterAuth.getAccountsList();
        shortAlert("Nombre : " + accountsList.length);

        for (Account currentAccount : accountsList)
        {
            Log.d("Rhymes", "KIKOO: " + currentAccount.name);
        }
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
