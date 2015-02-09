package com.is3261.customUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.is3261.GoWhereNUS.R;

public class CommetListAdapter extends BaseAdapter {

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

	public CommetListAdapter(Context context, String comments) {
		this.context = context;
		this.list = comments;
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
		JSONArray comments;
		try {
			JSONObject json = new JSONObject(list);
			comments = json.getJSONArray("comments");
			System.out.println("in adpater!!! " + comments.toString());
			ViewHolder viewHolder = null;
			if (convertView == null) {
				inflater = LayoutInflater.from(parent.getContext());
				convertView = inflater.inflate(R.layout.list_comment_item, null);
				viewHolder = new ViewHolder();

				viewHolder.rating = (RatingBar) convertView
						.findViewById(R.id.ratingBar1);
				viewHolder.userComment = (TextView) convertView
						.findViewById(R.id.userComment);
				viewHolder.commentTime = (TextView) convertView
						.findViewById(R.id.commentTime);
				viewHolder.image = (ImageView) convertView.findViewById(R.id.userPic);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			String titleStr;

			titleStr = ((JSONObject) comments.get(position))
					.getString("description");
			viewHolder.userComment.setText(titleStr);
			
			/*
			String time;

			time = ((JSONObject) comments.get(position)).getString("createdAt");
			viewHolder.commentTime.setText(time);
			*/
			int rating;
			rating = ((JSONObject) comments.get(position)).getInt("rating");
			System.out.println("rating is "+rating);
			viewHolder.rating.setRating(Float.parseFloat(Integer.toString(rating)));
			switch(position%4){
				case 0:
					viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile1));
					break;
				case 1:
					viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile2));
					break;
				case 2:
					viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile3));
					break;
				case 3:
					viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile4));
					break;				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertView;
	}

	static class ViewHolder {
		public RatingBar rating;
		public TextView userComment;
		public TextView commentTime;
		public ImageView image;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			JSONObject json = new JSONObject(this.list);
			JSONArray array = json.getJSONArray("comments");
			count = array.length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
