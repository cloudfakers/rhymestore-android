package com.rhymestore.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListRhymesActivity extends Activity
{
    private ArrayList<Rhyme> listRhymes;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_rhymes);

        listRhymes = new ArrayList<Rhyme>();
        listRhymes.add(new Rhyme("A empicharte ya estoy presto"));
        listRhymes.add(new Rhyme("A tu culo le declaro la guerra"));
        listRhymes.add(new Rhyme("Abracadabra tápate guarra"));
        listRhymes.add(new Rhyme("Agáchate que te la meto de lado"));
        listRhymes.add(new Rhyme("Agáchate que te vacuno"));

        // Prepare the ListView
        ListView list = (ListView) findViewById(R.id.list_rhymes);
        ListAdapter mAdapter = new ListRhymesAdapter(this, listRhymes);
        list.setAdapter(mAdapter);

        registerForContextMenu(list);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_rhymes_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        if (item.getItemId() == R.id.menu_add)
        {
            startActivity(new Intent(ListRhymesActivity.this, AddRhymesActivity.class));
            return true;
        }

        return false;
    }
}
