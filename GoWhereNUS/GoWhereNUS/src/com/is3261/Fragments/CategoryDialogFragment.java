package com.is3261.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.is3261.GoWhereNUS.MenuActivity;
import com.is3261.GoWhereNUS.R;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class CategoryDialogFragment extends DialogFragment {

	MenuActivity parent;

	public CategoryDialogFragment() {
		// Required empty public constructor
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parent = (MenuActivity) getActivity();
		getDialog().setTitle("Filter");
		View rootView = inflater.inflate(R.layout.layout_category_panel,
				container, false);

		final RadioGroup radioGroup = (RadioGroup) rootView
				.findViewById(R.id.radioGroup1);
		Button button = (Button) rootView.findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int radioId = radioGroup.getCheckedRadioButtonId();
				switch (radioId) {

				case R.id.radioAll:
					System.out.println("Radio all");
					parent.updateListView("All");
					getDialog().dismiss();
					break;
					
				case R.id.radioRain:
					parent.updateListView("Rain Sheltered");
					getDialog().dismiss();
					break;

				case R.id.radioHealthy:
					parent.updateListView("Healthy");
					getDialog().dismiss();
					break;

				}

			}

		});

		return rootView;
	}

}
