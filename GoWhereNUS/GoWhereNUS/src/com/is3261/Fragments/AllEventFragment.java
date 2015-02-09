package com.is3261.Fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.is3261.GoWhereNUS.MenuActivity;
import com.is3261.GoWhereNUS.R;
import com.is3261.Objects.Event;
import com.is3261.Objects.Route;
import com.is3261.customUI.CustomList;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.special.ResideMenu.ResideMenu;

public class AllEventFragment extends Fragment {

	private View parentView;
	private ResideMenu resideMenu;
	private ProgressDialog progressDialog;
	private ArrayList<Route> routes;
	private String choice;
	String[] titleArray;
	ListView list;

	public AllEventFragment() {

	}

	public AllEventFragment(String choice) {
		this.choice = choice;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentView = inflater.inflate(R.layout.all_events, container, false);
		
		Button comment;
		comment = (Button) getActivity().findViewById(R.id.title_bar_right_menu);
		comment.setVisibility(View.GONE);
		
		setUpViews();
		Button category = (Button) parentView
				.findViewById(R.id.categoryOptions);
		if (this.choice == null)
			category.setText("Filter");
		else
			category.setText(choice);
		// For test purpose only, list to be replaced by all events fetched from
		// online
		/*
		 * String[] web = { "Google Plus", "Twitter", "Windows", "Bing",
		 * "Itunes", "Wordpress", "Drupal", "OSU!", "HAHA", "RenRen", "Weibo" };
		 */
		new RemoteDataTask().execute();

		return parentView;

	}

	private void setUpViews() {
		MenuActivity parentActivity = (MenuActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();
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

			routes = new ArrayList<Route>();

			ParseQuery<ParseObject> query = ParseQuery.getQuery("Routes");

			List<ParseObject> objects;
			try {
				objects = query.find();
				System.out.println("Reach objects! and the first object is "
						+ objects.get(0).getString("steps"));
				int i;

				if (choice == null || choice.toUpperCase().equals("ALL")) {

					for (i = 0; i < objects.size(); i++) {

						ParseObject obj = objects.get(i);
						Route route = new Route();

						route.setId(obj.getObjectId());
						route.setStart(obj.getString("start"));
						route.setEnd(obj.getString("end"));
						route.setRainShelter(obj.getBoolean("rainshelter"));
						route.setHealthy(obj.getBoolean("healthy"));
						route.setSteps(obj.getString("steps"));
						route.setLazy(obj.getBoolean("lazy"));
						
						routes.add(route);

					}

				} else if (choice.toUpperCase().equals("RAIN SHELTERED")) {
					// user choice is rain sheltered
					for (i = 0; i < objects.size(); i++) {

						ParseObject obj = objects.get(i);
						if (obj.getBoolean("rainshelter")) {
							Route route = new Route();

							route.setId(obj.getObjectId());
							route.setStart(obj.getString("start"));
							route.setEnd(obj.getString("end"));
							route.setRainShelter(obj.getBoolean("rainshelter"));
							route.setHealthy(obj.getBoolean("healthy"));
							route.setSteps(obj.getString("steps"));

							routes.add(route);
						}
					}

				} else if (choice.toUpperCase().equals("HEALTHY")) {
					// user choice is health
					for (i = 0; i < objects.size(); i++) {

						ParseObject obj = objects.get(i);
						if (obj.getBoolean("healthy")) {
							Route route = new Route();

							route.setId(obj.getObjectId());
							route.setStart(obj.getString("start"));
							route.setEnd(obj.getString("end"));
							route.setRainShelter(obj.getBoolean("rainshelter"));
							route.setHealthy(obj.getBoolean("healthy"));
							route.setSteps(obj.getString("steps"));

							routes.add(route);
						}
					}
				}

				System.out.println(routes.size());

				titleArray = new String[routes.size()];

				for (int j = 0; j < titleArray.length; j++) {
					titleArray[j] = "" + routes.get(j).getStart() + " to "
							+ routes.get(j).getEnd();
					System.out.println(titleArray[j]);
				}

				System.out.println("Events find! size: " + objects.size());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Void result) {

			progressDialog.dismiss();

			CustomList adapter = new CustomList(getActivity(), routes,
					titleArray);

			list = (ListView) parentView.findViewById(R.id.listAllEvents);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String routeId = routes.get(position).getId();
					System.out.println(routeId);
					((MenuActivity) getActivity()).goOtherFragment(new SingleCommentFragment(routeId));
				}
			});

		}
	}
}
