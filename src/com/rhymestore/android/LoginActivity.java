package com.rhymestore.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Set the font of the TextView and EditText
        // EditText edit_login = (EditText) findViewById(R.id.edit_login);
        // this.setFont(edit_login, "arial");
        // EditText edit_pass = (EditText) findViewById(R.id.edit_pass);
        // this.setFont(edit_pass, "arial");
        // TextView text_login = (TextView) findViewById(R.id.text_login);
        // this.setFont(text_login, "arial");
        // TextView text_pass = (TextView) findViewById(R.id.text_pass);
        // this.setFont(text_pass, "arial");

        // Handle event on connexion button
        ImageButton connexionImageButton = (ImageButton) findViewById(R.id.connexionImageButton);
        connexionImageButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(final View v)
            {
                if (connexionUser() == true)
                {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        });

        // Get user SharedPreferences and match the datas
        matchPreferences();
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

    private boolean connexionUser()
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

        // Connect to the database
        alert("Connexion...");

        return true;
    }

    /**
     * Get the required datas in the SharedPreferences to fill the form if they exists
     */
    private void matchPreferences()
    {
        SharedPreferences config = getSharedPreferences(Configuration.PREFS_NAME, 0);

        // Get the preferences of the login form
        String loginPrefUser = config.getString(Configuration.PREFS_PREFIX_KEY + "login", null);
        String passPrefuser = config.getString(Configuration.PREFS_PREFIX_KEY + "pass", null);
        Boolean rememberPrefUser =
            config.getBoolean(Configuration.PREFS_PREFIX_KEY + "remember", false);

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
            this.getSharedPreferences(Configuration.PREFS_NAME, 0).edit();

        prefs.putString(Configuration.PREFS_PREFIX_KEY + "login", login);
        prefs.putString(Configuration.PREFS_PREFIX_KEY + "pass", pass);
        prefs.putBoolean(Configuration.PREFS_PREFIX_KEY + "remember", remember);

        prefs.commit();
    }

    /**
     * Delete all preferences of the user from the SharedPreferences
     */
    private void deletePreferences()
    {
        SharedPreferences config = getSharedPreferences(Configuration.PREFS_NAME, 0);
        SharedPreferences.Editor edit = config.edit();

        edit.remove(Configuration.PREFS_PREFIX_KEY + "login");
        edit.remove(Configuration.PREFS_PREFIX_KEY + "pass");
        edit.remove(Configuration.PREFS_PREFIX_KEY + "remember");

        edit.commit();
    }

    private void alert(final String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
