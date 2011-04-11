package com.rhymestore.android;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rhymestore.android.rhymes.ListRhymesActivity;

public class HomeActivity extends Activity implements OnInitListener
{
    private TextToSpeech textToSpeech;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        textToSpeech = new TextToSpeech(this, this);
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

    @Override
    public void onInit(final int status)
    {
        if (status == TextToSpeech.SUCCESS)
        {
            int result = textToSpeech.setLanguage(new Locale("spa", "ESP"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                shortAlert("Language is not available.");
            }
            else
            {
                rhymeSpeak("¿Tu también tienes un amigo subnormal?");
            }
        }
        else
        {
            shortAlert("Could not initialize TextToSpeech.");
        }
    }

    private void rhymeSpeak(final String sentence)
    {
        textToSpeech.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void shortAlert(final String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
