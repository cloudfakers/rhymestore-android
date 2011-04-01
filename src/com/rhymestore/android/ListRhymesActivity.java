package com.rhymestore.android;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListRhymesActivity extends Activity
{
    /** Contains the list of all rhymes */
    private ArrayList<Rhyme> listRhymes;

    /** The API URL */
    private String urlApi = "http://10.60.1.222:8888/rhymestore/web/api";

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_rhymes);

        // Get all the rhymes performing a GET request to the API
        listRhymes = new ArrayList<Rhyme>();
        listRhymes = getListRhymes();

        if (listRhymes != null)
        {
            // Prepare the ListView
            ListView list = (ListView) findViewById(R.id.list_rhymes);
            ListAdapter mAdapter = new ListRhymesAdapter(this, listRhymes);
            list.setAdapter(mAdapter);

            registerForContextMenu(list);
        }
        else
        {
            // Close this activity
            finish();

            longAlert("Sorry ! An error has occured, the application could not get the rhymes list.");
        }
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

    /**
     * Performs a GET request to the API and retrieves the list of all the rhymes
     * 
     * @return A ArrayList witch contains all the rhymes
     */
    private ArrayList<Rhyme> getListRhymes()
    {
        ArrayList<Rhyme> returnList = new ArrayList<Rhyme>();

        try
        {
            // Connect to the API
            URL u = new URL(getUrlApi());
            InputStream in = u.openStream();
            InputSource ipsrc = new InputSource(in);

            // Parse the XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ipsrc);
            doc.getDocumentElement().normalize();

            // Manage results from the XML to get the rhymes
            NodeList nList = doc.getElementsByTagName("rhyme");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;

                    String currentValue = getTagValue("rhyme", eElement);
                    if (currentValue != null)
                    {
                        returnList.add(new Rhyme(currentValue));
                    }
                }
            }
        }
        catch (Exception ex)
        {
            return null;
        }

        return returnList;
    }

    /**
     * Get the tag value of the current rhyme
     * 
     * @param tag Name of the tag
     * @param element Element to check
     * @return Value of the tag
     */
    private static String getTagValue(final String tag, final Element element)
    {
        NodeList nlList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);

        return nValue.getNodeValue();
    }

    public void setUrlApi(final String url_api)
    {
        this.urlApi = url_api;
    }

    public String getUrlApi()
    {
        return urlApi;
    }

    private void shortAlert(final String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void longAlert(final String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
