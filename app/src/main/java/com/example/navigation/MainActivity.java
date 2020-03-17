package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.navigation.R.menu.toolbar;

public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
    private Drawer drawer;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    @BindView(R.id.frame_container)
    FrameLayout containerFrame;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inflateNavViewer();


//        sketch = new Sketch();
//        PFragment pfragment = new PFragment(sketch);
//        pfragment.setView(containerFrame,MainActivity.this);
    }
    public void inflateNavViewer(){
        toolbar=findViewById(R.id.tool_bar);
        IProfile profile=new ProfileDrawerItem().
                withName("Databyte").
                withIcon(R.drawable.ic_launcher_background);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorSecondary)
                .addProfiles(profile)
                .withCompactStyle(true)
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("home").withIcon(R.drawable.home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("myprofile").withIcon(R.drawable.profile);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("mlalgos").withIcon(R.drawable.ic_search);

        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withName("menu").withIcon(R.drawable.ic_menu_black_24dp);
        SecondaryDrawerItem item6 = new SecondaryDrawerItem().withIdentifier(6).withName("feedback").withIcon(R.drawable.feedback);
        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(7).withName("helpcentre").withIcon(R.drawable.help);
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder.withActivity(this);
        drawerBuilder.withToolbar(toolbar);
        drawerBuilder.withHasStableIds(true);
        drawerBuilder.withDrawerLayout(R.layout.crossfade_drawer);
        drawerBuilder.withAccountHeader(headerResult);
        drawerBuilder.withDrawerWidthDp(72);
        drawerBuilder.withGenerateMiniDrawer(true);
        drawerBuilder.withTranslucentStatusBar(true);
        drawerBuilder.withActionBarDrawerToggleAnimated(true);
        drawerBuilder.addDrawerItems(
                item1,
                item2,
                item3,
                new DividerDrawerItem(),
                item5,
                item6,
                item7
        );
        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Fragment fragment = null;
                switch (position) {
                    case 1:
                        if (drawer != null && drawer.isDrawerOpen()) {
                            drawer.closeDrawer();
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        fragment = new AlgosFragment();
                        break;
                    case 5:
                        fragment = new Aboutus();
                        break;
                    default:
                        break;
                }
                if (fragment == null) {
                    return true;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                closedraw();
                return true;
            }
        });
        drawer = drawerBuilder.build();

        crossfadeDrawerLayout = (CrossfadeDrawerLayout) drawer.getDrawerLayout();

        //define maxDrawerWidth

        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));

        final MiniDrawer miniResult = drawer.getMiniDrawer();

        View view = miniResult.build(this);

        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));

        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    drawer.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar,menu);
        return true;
    }
    public void closedraw(){
        drawer.closeDrawer();
    }
    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
