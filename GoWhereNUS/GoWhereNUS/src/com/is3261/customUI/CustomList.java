package com.is3261.customUI;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.is3261.GoWhereNUS.R;
import com.is3261.Objects.Event;
import com.is3261.Objects.Route;

public class CustomList extends ArrayAdapter<String> {
	private final Activity context;
	private ArrayList<Route> events;
	private final String[] titles;

	public CustomList(Activity context, ArrayList<Route> web,String[] titles) {
		super(context, R.layout.list_view_all,titles);
		this.context = context;
		this.events = web;
		this.titles = titles;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_view_all, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.eventTitle);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		TextView dateTxt = (TextView) rowView.findViewById(R.id.totalTime);
		
		
		txtTitle.setText(titles[position]);
		try {
			dateTxt.setText("Estimated total time: "+ Float.toString(events.get(position).getTotaltime())+" mins");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(position%2 == 0)
			rowView.setBackgroundColor(Color.TRANSPARENT);
				
		//imageView.setImageResource(imageId[position]);
		return rowView;
	}
}
