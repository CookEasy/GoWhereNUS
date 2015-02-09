package com.is3261.Fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.is3261.GoWhereNUS.R;
import com.is3261.customUI.TimelineAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * User: special Date: 13-12-22 Time: 下午1:33 Mail: specialcyci@gmail.com
 */
public class SingleCommentFragment extends Fragment {

	private View parentView;
	private ListView listView;
	private TimelineAdapter timelineAdapter;
	private String routeid;
	public String steps; // json steps
	public ProgressDialog progressDialog;
	public Context context;
	public Button comment;
	private PopupWindow mPopupWindow; // used for input comment
	private View popupView;
	
	public SingleCommentFragment(String id) {
		this.routeid = id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentView = inflater.inflate(R.layout.event_detail_single, container,
				false);
		comment = (Button) getActivity()
				.findViewById(R.id.title_bar_right_menu);
		comment.setVisibility(View.VISIBLE);

		context = getActivity();
		listView = (ListView) parentView.findViewById(R.id.listStep);

		popupView = inflater.inflate(R.layout.step_detail, null);

		mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				800, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.grey_bg));

		mPopupWindow.getContentView().setFocusableInTouchMode(true);
		mPopupWindow.getContentView().setFocusable(true);
		mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);

		new RemoteDataTask().execute();

		listView.setDividerHeight(0);

		return parentView;
	}

	// below is the class for network calls
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// Create a progressdialog

			progressDialog = new ProgressDialog(getActivity());
			// Set progressdialog title
			progressDialog.setTitle("Progress");
			progressDialog.setMessage("Getting data");

			// Set progressdialog message
			progressDialog.setIndeterminate(true);
			// Show progressdialog
			progressDialog.show();

		}

		@SuppressWarnings("deprecation")
		@Override
		protected Void doInBackground(Void... params) {

			ParseQuery<ParseObject> query = ParseQuery.getQuery("Routes");

			// Retrieve the object by id
			query.getInBackground(routeid, new GetCallback<ParseObject>() {
				public void done(ParseObject route, ParseException e) {
					if (e == null) {
						// Now let's update it with some new data. In this case,
						// only cheatMode and score
						// will get sent to the Parse Cloud. playerName hasn't
						// changed.
						steps = route.getString("steps");
						System.out.println(routeid);
						timelineAdapter = new TimelineAdapter(context, steps);
						listView.setAdapter(timelineAdapter);

						listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
									JSONArray stepArray;
									try {
										
										JSONObject json = new JSONObject(steps);
										stepArray = json.getJSONArray("steps");
										//special id cGERiXsUFm (route KR MRT to CLB)
										if(routeid.equals("EuZ54xEN9d")){
											ImageView image = (ImageView) popupView.findViewById(R.id.imageViewStepDetail);
											switch (position) {
												case 1:
													System.out.println("enter switch block");
													image.setImageResource(R.drawable.step6_0);
													break;
												case 2:
													image.setImageResource(R.drawable.step6_1);
													break;
												case 3:
													image.setImageResource(R.drawable.step6_2);
													break;
												case 4:
													image.setImageResource(R.drawable.step6_3);
													break;
												case 5:
													image.setImageResource(R.drawable.step6_4);
													break;
											}
											
										}
										
										TextView description = (TextView) popupView.findViewById(R.id.description);
										JSONObject obj = (JSONObject) stepArray.get(position);
										description.setText(obj.getString("description"));
										
										TextView time = (TextView) popupView.findViewById(R.id.time);
										time.setText(""+obj.getInt("time")+" mins");
										
										mPopupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										Toast toast = Toast.makeText(getActivity(), "An error happens", Toast.LENGTH_SHORT);
										toast.show();
										e.printStackTrace();
									}	
							}
						});

						progressDialog.dismiss();
					}
				}
			});

			return null;
		}

		protected void onPostExecute(Void result) {

		}
	}
}
