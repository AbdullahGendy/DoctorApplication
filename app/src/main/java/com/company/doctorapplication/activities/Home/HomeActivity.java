package com.company.doctorapplication.activities.Home;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.activities.Home.Fragments.ProfileFragment.ProfileFragment;
import com.company.doctorapplication.activities.Home.Fragments.Reservation.ReservationFragment;
import com.company.doctorapplication.activities.Home.Fragments.ReviewsFragment.ReviewsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(Constant.rememberMe, false)) {
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString(Constant.accessToken, "");
            edit.putString(Constant.notificationToken, "");
            edit.putBoolean(Constant.rememberMe, false);
            edit.putString(Constant.logoutNotificationTitle, "");
            edit.putString(Constant.logoutNotificationBody, "");
            edit.apply();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager viewPager = findViewById(R.id.home_view_pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReviewsFragment(), "Reviews");
        adapter.addFragment(new ProfileFragment(), "Profile");
        adapter.addFragment(new ReservationFragment(), "Reservation");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
