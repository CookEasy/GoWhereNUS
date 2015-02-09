package com.is3261.GoWhereNUS;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ViewAnimator;

import com.is3261.Fragments.AddRouteFragment;
import com.is3261.Fragments.AllEventFragment;
import com.is3261.Fragments.CategoryDialogFragment;
import com.is3261.Fragments.HomeFragment;
import com.is3261.Fragments.SearchFragment;
import com.is3261.Fragments.SettingsFragment;
import com.is3261.GoWhereNUS.R;
import com.is3261.Objects.User;
import com.is3261.customUI.AnimationFactory;
import com.is3261.customUI.AnimationFactory.FlipDirection;
import com.parse.Parse;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem itemAllEvents;
    private ResideMenuItem itemSearch;
    private User currentUser;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		Parse.initialize(this, "87oAquBWb0k4smKAWCDeXEC3RnT8g8XyeXDlDmL4", "OcxglNUQPt3Ry8movAGHGsyujf8lz0U0zKkq98Wp");

        //get user information 
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("user");
        System.out.println("Current user is"+ currentUser.getUsername());
        
        
        
        
        mContext = this;
        setUpMenu();
        changeFragment(new HomeFragment(currentUser));
        
        
        
              
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.home_wt,     "Home");
        itemCalendar = new ResideMenuItem(this, R.drawable.add_route_wt, "Add a route");
        itemSettings = new ResideMenuItem(this, R.drawable.settings_wt2, "Settings");
        itemAllEvents = new ResideMenuItem(this,R.drawable.display_all_route_wt, "All Routes");
        itemSearch = new ResideMenuItem(this,R.drawable.search_route_wt2,"Search a route");

        itemHome.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        itemAllEvents.setOnClickListener(this);
        itemSearch.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAllEvents, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSearch, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	//Open Left Menu
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
     
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment(currentUser));
        }else if (view == itemCalendar){
            changeFragment(new AddRouteFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }else if (view == itemAllEvents){
        	changeFragment(new AllEventFragment());
        }else if (view == itemSearch){
        	changeFragment(new SearchFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        	//OnMenuListener to be implemented
        }

        @Override
        public void closeMenu() {
        	//to be implemented
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
    
    public void addRoute(View view){
    	
       final ViewAnimator viewAnimator = (ViewAnimator)this.findViewById(R.id.viewFlipper);

    	AnimationFactory.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT);
    }
    
    
    //begin of method used for allEvent fragment
    public void goOtherFragment(Fragment targetFragment){
    	
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    	
    	transaction.replace(R.id.main_fragment, targetFragment);
    	transaction.addToBackStack(null);
    	transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	
    	transaction.commit();
    }
    
    public void selectCategory(View view){
		CategoryDialogFragment dialog = new CategoryDialogFragment();
		dialog.setCancelable(false);
	    FragmentManager fm = getSupportFragmentManager();
		
        dialog.show(fm, "Dialog Fragment");
			
	}
    
    public void updateListView(String choice){
    
    		goOtherFragment(new AllEventFragment(choice));
    	
    }
    
  
    
    //end of methods for all event fragment
}
