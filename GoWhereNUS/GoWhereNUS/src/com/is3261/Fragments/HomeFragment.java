package com.is3261.Fragments;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.is3261.GoWhereNUS.MenuActivity;
import com.is3261.GoWhereNUS.R;
import com.is3261.Objects.User;
import com.special.ResideMenu.ResideMenu;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:33
 * Mail: specialcyci@gmail.com
 */
public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private User currentUser;
    ImageView nusmap;
    PhotoViewAttacher attacher;
    
    public HomeFragment(){
    	
    }
    
    public HomeFragment(User user){
    	this.currentUser = user;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        
        Button comment;
		comment = (Button) getActivity().findViewById(R.id.title_bar_right_menu);
		comment.setVisibility(View.GONE);
        setUpViews();
        
        nusmap = (ImageView) parentView.findViewById(R.id.nusmap);
        attacher = new PhotoViewAttacher(nusmap);
        
        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

       

        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }

}
