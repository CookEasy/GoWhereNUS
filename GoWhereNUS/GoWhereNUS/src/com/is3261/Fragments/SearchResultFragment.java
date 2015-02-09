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

public class SearchResultFragment extends Fragment {

	private View parentView;
	private ResideMenu resideMenu;
	private ProgressDialog progressDialog;
	private ArrayList<Route> routes;
	private String choice;
	String[] titleArray;
	ListView list;

	public SearchResultFragment(ArrayList<Route> routes) {
		this.routes =  routes;
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
		titleArray = new String[routes.size()];

		for (int j = 0; j < titleArray.length; j++) {
			titleArray[j] = "" + routes.get(j).getStart() + " to "
					+ routes.get(j).getEnd();
			System.out.println(titleArray[j]);
		}

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
		
		return parentView;

	}

	private void setUpViews() {
		MenuActivity parentActivity = (MenuActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();
	}

	// below is the class for network calls
	


		
	
}
