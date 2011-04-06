package com.rhymestore.android;

import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rhymestore.android.accountsmanager.TwitterAccountManager;
import com.rhymestore.android.configuration.Preferences;

public class LoginActivity extends Activity
{
    private static final int DIALOG_LOADING_AUTH = 0;

    private TwitterAccountManager twitterAccountManager;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        twitterAccountManager = new TwitterAccountManager(this);

        // Handle event on connexion button
        ImageButton connexionImageButton = (ImageButton) findViewById(R.id.connexionImageButton);
        connexionImageButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(final View v)
            {
                // Check validity of the form
                if (checkForm() == true)
                {
                    // Connect to Twitter
                    try
                    {
                        twitterAccountManager.requestAuthentication();
                        // TwitterAuth twitterAuth = new TwitterAuth();
                        // return twitterAuth.execute().get();
                    }
                    catch (Exception ex)
                    {
                        alert("Error:" + ex.getMessage());
                    }
                }
            }
        });

        // Get user SharedPreferences and match the datas
        matchPreferences();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Uri uri = getIntent().getData();

        if (uri != null)
        {
            try
            {
                String oauthVerifier = uri.getQueryParameter("oauth_verifier");

                // Try identification with feedback from the Twitter validation
                if (twitterAccountManager.tryAuthentication(oauthVerifier) == true)
                {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
                else
                {
                    alert("Error with Twitter authentication !");
                }
            }
            catch (Exception ex)
            {
                alert("Error: " + ex.getMessage());
                Log.d("RHYME", "HEY: " + ex.getMessage());
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(final int id)
    {
        if (id == DIALOG_LOADING_AUTH)
        {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Twitter Authentication");
            dialog.setMessage("Please wait why authenticating to your Twitter account...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);

            return dialog;
        }

        return null;
    }

    /**
     * Thread to manage the Twitter Authentication using a loading dialog
     * 
     * @author vmahe
     */
    private class TwitterAuth extends AsyncTask<URL, Integer, Boolean>
    {
        @Override
        protected void onPreExecute()
        {
            showDialog(DIALOG_LOADING_AUTH);
        }

        @Override
        protected Boolean doInBackground(final URL... urls)
        {
            try
            {
                twitterAccountManager.requestAuthentication();
                return true;
            }
            catch (Exception ex)
            {
                alert("Error: " + ex.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean bool)
        {
            dismissDialog(DIALOG_LOADING_AUTH);
        }
    }

    /**
     * Try to connect the user to his Twitter account
     * 
     * @return if the connexion is successful or failed
     */
    private boolean checkForm()
    {
        // Check the validity of the fields
        EditText et_login = (EditText) findViewById(R.id.edit_login);
        EditText et_pass = (EditText) findViewById(R.id.edit_pass);
        CheckBox cb_remember = (CheckBox) findViewById(R.id.rememberCheckbox);

        String user_login = et_login.getText().toString();
        String user_pass = et_pass.getText().toString();
        boolean user_remember = cb_remember.isChecked();

        if (user_login.equals("") || user_pass.equals(""))
        {
            alert("Error: Please fill all fields !");
            return false;
        }

        // Sauvegarder les préférences de l'utilisateur s'il coche "Mémoriser le login"
        if (user_remember == true)
        {
            savePreferences(user_login, user_pass, user_remember);
        }
        else
        {
            deletePreferences();
        }

        return true;
    }

    /**
     * Set font to all EditText and TextView
     */
    private void setAllFont()
    {
        // Set the font of the TextView and EditText
        EditText edit_login = (EditText) findViewById(R.id.edit_login);
        this.setFont(edit_login, "arial");

        EditText edit_pass = (EditText) findViewById(R.id.edit_pass);
        this.setFont(edit_pass, "arial");

        TextView text_login = (TextView) findViewById(R.id.text_login);
        this.setFont(text_login, "arial");

        TextView text_pass = (TextView) findViewById(R.id.text_pass);
        this.setFont(text_pass, "arial");
    }

    /**
     * Set the font of a TextView
     * 
     * @param textView The TextView to change font
     * @param font The name of the font
     */
    private void setFont(final TextView textView, final String font)
    {
        String textViewFont = "fonts/" + font + ".ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), textViewFont);
        textView.setTypeface(tf);
    }

    /**
     * Set the font of a TextView
     * 
     * @param textView The TextView to change font
     * @param font The name of the font
     */
    private void setFont(final EditText editText, final String font)
    {
        String textViewFont = "fonts/" + font + ".ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), textViewFont);
        editText.setTypeface(tf);
    }

    /**
     * Get the required datas in the SharedPreferences to fill the form if they exists
     */
    private void matchPreferences()
    {
        SharedPreferences config = getSharedPreferences(Preferences.PREFS_NAME, 0);

        // Get the preferences of the login form
        String loginPrefUser = config.getString(Preferences.PREFS_PREFIX_KEY + "login", null);
        String passPrefuser = config.getString(Preferences.PREFS_PREFIX_KEY + "pass", null);
        Boolean rememberPrefUser =
            config.getBoolean(Preferences.PREFS_PREFIX_KEY + "remember", false);

        // Match the datas of the SharedPreferences to the form
        EditText loginEditText = (EditText) findViewById(R.id.edit_login);
        EditText passEditText = (EditText) findViewById(R.id.edit_pass);
        CheckBox rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckbox);

        loginEditText.setText(loginPrefUser);
        passEditText.setText(passPrefuser);
        rememberCheckBox.setChecked(rememberPrefUser);
    }

    /**
     * Save the user preferences of the login form in the SharedPreferences
     * 
     * @param login The value of the EditText login of the form
     * @param pass The value of the EditText pass of the form
     * @param remember The value of the Checkbox remember of the form
     */
    private void savePreferences(final String login, final String pass, final boolean remember)
    {
        SharedPreferences.Editor prefs =
            this.getSharedPreferences(Preferences.PREFS_NAME, 0).edit();

        prefs.putString(Preferences.PREFS_PREFIX_KEY + "login", login);
        prefs.putString(Preferences.PREFS_PREFIX_KEY + "pass", pass);
        prefs.putBoolean(Preferences.PREFS_PREFIX_KEY + "remember", remember);

        prefs.commit();
    }

    /**
     * Delete all preferences of the user from the SharedPreferences
     */
    private void deletePreferences()
    {
        SharedPreferences config = getSharedPreferences(Preferences.PREFS_NAME, 0);
        SharedPreferences.Editor edit = config.edit();

        edit.remove(Preferences.PREFS_PREFIX_KEY + "login");
        edit.remove(Preferences.PREFS_PREFIX_KEY + "pass");
        edit.remove(Preferences.PREFS_PREFIX_KEY + "remember");

        edit.commit();
    }

    private void alert(final String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
