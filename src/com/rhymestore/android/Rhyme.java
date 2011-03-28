package com.rhymestore.android;

public class Rhyme
{
    private String text;

    public Rhyme()
    {
    }

    public Rhyme(final String text)
    {
        setText(text);
    }

    public void setText(final String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
