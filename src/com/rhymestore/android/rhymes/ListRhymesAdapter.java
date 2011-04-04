package com.rhymestore.android.rhymes;

import java.util.List;

import com.rhymestore.android.R;
import com.rhymestore.android.R.id;
import com.rhymestore.android.R.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListRhymesAdapter extends BaseAdapter
{

    private LayoutInflater mInflater;

    private int[] colors;

    private List<Rhyme> listRhymes;

    public ListRhymesAdapter(final Context context, final List<Rhyme> objects)
    {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        this.mInflater = LayoutInflater.from(context);

        // Get the rhymes
        this.listRhymes = objects;

        // Add the colors for the alternative background
        this.colors = new int[2];
        this.colors[0] = Color.WHITE;
        this.colors[1] = Color.LTGRAY;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.rhyme_itemlist, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.toptext = (TextView) convertView.findViewById(R.id.toptext);

            convertView.setTag(holder);
        }
        else
        {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.toptext.setText(this.listRhymes.get(position).getText());

        // Alterner le background de l'item
        convertView.setBackgroundColor(this.colors[position % this.colors.length]);

        return convertView;
    }

    static class ViewHolder
    {
        TextView toptext;
    }

    @Override
    public int getCount()
    {
        return this.listRhymes.size();
    }

    @Override
    public Object getItem(final int position)
    {
        return position;
    }

    @Override
    public long getItemId(final int position)
    {
        return position;
    }

}
