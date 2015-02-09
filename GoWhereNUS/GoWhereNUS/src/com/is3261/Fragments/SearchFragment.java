package com.is3261.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.is3261.GoWhereNUS.MenuActivity;
import com.is3261.GoWhereNUS.R;
import com.is3261.Objects.Route;
import com.is3261.customUI.CustomList;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.special.ResideMenu.ResideMenu;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class SearchFragment extends Fragment {

	private View parentView;
	private ResideMenu resideMenu;
	private AutoCompleteTextView from;
	private AutoCompleteTextView to;
	private ViewGroup mContainerView; // used in add_route, cross fade effect
	private CheckBox lazy;
	private CheckBox healthy;
	private CheckBox shelter;
	private CheckBox all;
	private ProgressDialog progressDialog;
	private ArrayList<Route> routes;
	private MenuActivity parentActivity;

	public SearchFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.search_layout, container, false);
		parentActivity = (MenuActivity) getActivity();

		String[] locations = parentView.getResources().getStringArray(
				R.array.locations);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				parentView.getContext(),
				R.layout.support_simple_spinner_dropdown_item, locations);

		from = (AutoCompleteTextView) parentView.findViewById(R.id.from);
		to = (AutoCompleteTextView) parentView.findViewById(R.id.to);
		lazy = (CheckBox) parentView.findViewById(R.id.checkBox3);
		healthy = (CheckBox) parentView.findViewById(R.id.checkBox2);
		shelter = (CheckBox) parentView.findViewById(R.id.checkBox1);
		all = (CheckBox) parentView.findViewById(R.id.checkBox4);

		Button searchButton = (Button) parentView
				.findViewById(R.id.buttonSearch);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Create a progressdialog
				/*
				 * progressDialog = new ProgressDialog(parentActivity); // Set
				 * progressdialog title progressDialog.setTitle("Progress");
				 * progressDialog.setMessage("Searching...");
				 * 
				 * // Set progressdialog message
				 * progressDialog.setIndeterminate(true); // Show progressdialog
				 * progressDialog.show();
				 */
				new RemoteDataTask().execute();
			}

		});

		from.setAdapter(adapter);
		to.setAdapter(adapter);

		setUpViews();
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

		}

		@SuppressWarnings("deprecation")
		@Override
		protected Void doInBackground(Void... params) {

			routes = new ArrayList<Route>();

			return null;
		}

		protected void onPostExecute(Void result) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Routes");
			
			if(!all.isChecked())
			query.whereEqualTo("start", from.getText().toString());
			query.whereEqualTo("end", to.getText().toString());
			query.whereEqualTo("lazy", lazy.isChecked());
			query.whereEqualTo("healthy", healthy.isChecked());
			query.whereEqualTo("rainshelter", shelter.isChecked());

			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> scoreList, ParseException e) {
					if (e == null && scoreList !=null) {
						System.out.println("find reslut of size: "
								+ scoreList.size());
						for (int i = 0; i < scoreList.size(); i++) {
							Route route = new Route();
							ParseObject obj = scoreList.get(i);

							route.setId(obj.getObjectId());
							route.setStart(obj.getString("start"));
							route.setEnd(obj.getString("end"));
							route.setRainShelter(obj.getBoolean("rainshelter"));
							route.setHealthy(obj.getBoolean("healthy"));
							route.setSteps(obj.getString("steps"));
							route.setLazy(obj.getBoolean("lazy"));

							routes.add(route);
						}
						
						((MenuActivity) getActivity())
								.goOtherFragment(new SearchResultFragment(routes));

					} else {
						Toast toast = Toast.makeText(getActivity(),
								"Sorry we cannot find any routes",
								Toast.LENGTH_SHORT);
						toast.show();
					}
				}
			});
			// progressDialog.dismiss();
			

		}
	}
}
