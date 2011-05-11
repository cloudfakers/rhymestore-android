package com.rhymestore.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnInitListener, OnClickListener
{
    private TextToSpeech textToSpeech;

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private ListView mList;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        textToSpeech = new TextToSpeech(this, this);

        // Get display items for later interaction
        Button speakButton = (Button) findViewById(R.id.btn_speak);

        mList = (ListView) findViewById(R.id.list);

        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities =
            pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0)
        {
            speakButton.setOnClickListener(this);
        }
        else
        {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
    }

    /**
     * Handle the click on the start recognition button.
     */
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_speak)
        {
            startVoiceRecognitionActivity();
        }
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
                // rhymeSpeak("¿Tu también tienes un amigo subnormal?");
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

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "spa-ESP");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something in spanish");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches =
                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                matches));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void shortAlert(final String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
