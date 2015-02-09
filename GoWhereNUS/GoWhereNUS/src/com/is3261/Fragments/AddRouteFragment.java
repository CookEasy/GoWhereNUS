package com.is3261.Fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.is3261.GoWhereNUS.MenuActivity;
import com.is3261.GoWhereNUS.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.special.ResideMenu.ResideMenu;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class AddRouteFragment extends Fragment {
	
	private View parentView;
	private ResideMenu resideMenu;
	private AutoCompleteTextView start;
	private AutoCompleteTextView end;
    private ViewGroup mContainerView; // used in add_route, cross fade effect
    private JSONObject json;
    private JSONArray array;
    private CheckBox rainshelter;
    private CheckBox healthy;
    private CheckBox lazy;
    private ParseObject parse;
    
	public AddRouteFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.add_route, container, false);
		
        mContainerView = (ViewGroup) parentView.findViewById(R.id.container);
        addItem();
		
		String[] locations = parentView.getResources().getStringArray(R.array.locations);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(parentView.getContext(),R.layout.support_simple_spinner_dropdown_item,locations);
		
		
		rainshelter = (CheckBox) parentView.findViewById(R.id.rain);
		healthy = (CheckBox) parentView.findViewById(R.id.healthy);
		lazy = (CheckBox) parentView.findViewById(R.id.lazy);
		
		start = (AutoCompleteTextView) parentView.findViewById(R.id.startAutoComplete);
		end = (AutoCompleteTextView) parentView.findViewById(R.id.endAutoComplete);
		
		start.setAdapter(adapter);
		end.setAdapter(adapter);
		
		
		setUpViews();
		
		 json = new JSONObject();
		 array = new JSONArray();
		 
		Button submit = (Button) parentView.findViewById(R.id.submitRoute);
		submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parse = new ParseObject("Routes");
				int num = mContainerView.getChildCount();
				System.out.println("Children count: "+num);
				for(int i=0;i<num;i++){
					View child = mContainerView.getChildAt(i);
					EditText mDescription = (EditText) child.findViewById(R.id.eventTitle);
					EditText mTime = (EditText) child.findViewById(R.id.eventDateAndTime);
					
					String description = mDescription.getText().toString();
					String time = mTime.getText().toString();
					
					JSONObject obj = new JSONObject();
					try {
						obj.put("description", description);
						obj.put("time", Integer.parseInt(time));
						array.put(obj);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					json.put("steps", array);
					parse.put("start", start.getText().toString());
					parse.put("end", end.getText().toString());
					parse.put("steps", json.toString());
					parse.put("rainshelter", rainshelter.isChecked());
					parse.put("healthy", healthy.isChecked());
					parse.put("lazy", lazy.isChecked());
					parse.saveInBackground(new SaveCallback(){

						@Override
						public void done(ParseException e) {
							// TODO Auto-generated method stub
							Toast toast = Toast.makeText(getActivity(), "Route saved successfully", Toast.LENGTH_SHORT);
							toast.show();
						}
						
					});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		return parentView;
	}
	
	private void setUpViews() {
		
		MenuActivity parentActivity = (MenuActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();
	}
	
	 private void addItem() {
	        // Instantiate a new "row" view.
	        final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
	                R.layout.list_single, mContainerView, false);
	        if(mContainerView.getChildCount()==0){
	        	Button cancel = (Button) newView.findViewById(R.id.buttonCancel);
	        	cancel.setVisibility(View.GONE);
	        }
	        
	        
	        // Set click listener button in the row that will remove the row.
	        newView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                // Remove the row from its parent (the container view).
	                // Because mContainerView has android:animateLayoutChanges set to true,
	                // this removal is automatically animated.
	                
	            	if(mContainerView.getChildCount()!=1)
	            	mContainerView.removeView(newView);
	                
	            }
	        });
	        
	        newView.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                // Remove the row from its parent (the container view).
	                // Because mContainerView has android:animateLayoutChanges set to true,
	                // this removal is automatically animated.
	                addItem();
	            }
	        }); 

	        // Because mContainerView has android:animateLayoutChanges set to true,
	        // adding this view is automatically animated.
	        mContainerView.addView(newView, 0);
	    }

}
