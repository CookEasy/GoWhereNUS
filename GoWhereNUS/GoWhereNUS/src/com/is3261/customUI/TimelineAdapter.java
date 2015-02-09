package com.is3261.customUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.is3261.GoWhereNUS.R;

public class TimelineAdapter extends BaseAdapter {

	private Context context;
	private String list; // list here is a json
	/*
	 * {"steps":[{"description":"Get out lecture hall and turn right","time":1},{
	 * "description":"turn left, go downstairs","time":1},{"description":
	 * "go straight, until you see a white door"
	 * ,"time":5},{"description":"Enter the white door, turn right"
	 * ,"time":4},{"description"
	 * :"You will see the hall on your left","time":2}]}
	 */
	private LayoutInflater inflater;

	public TimelineAdapter(Context context, String steps) {
		this.context = context;
		this.list = steps;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		JSONArray steps;
		try {
			JSONObject json = new JSONObject(list);
			steps = json.getJSONArray("steps");
			System.out.println("in adpater!!! " + steps.toString());
			ViewHolder viewHolder = null;
			if (convertView == null) {
				inflater = LayoutInflater.from(parent.getContext());
				convertView = inflater.inflate(R.layout.listview_item, null);
				viewHolder = new ViewHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.title);
				viewHolder.number = (TextView) convertView
						.findViewById(R.id.show_time);
				viewHolder.esitimateTime = (TextView) convertView
						.findViewById(R.id.textViewEstimate);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			String titleStr;

			titleStr = ((JSONObject) steps.get(position))
					.getString("description");

			String time;

			time = Integer.toString(((JSONObject) steps.get(position))
					.getInt("time"));
			viewHolder.esitimateTime
					.setText("Estimate time: " + time + " mins");
			viewHolder.number.setText("Step " + (position + 1) + " ");
			viewHolder.title.setText(titleStr);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertView;
	}

	static class ViewHolder {
		public TextView number;
		public TextView esitimateTime;
		public TextView title;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			JSONObject json = new JSONObject(this.list);
			JSONArray array = json.getJSONArray("steps");
			count = array.length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
