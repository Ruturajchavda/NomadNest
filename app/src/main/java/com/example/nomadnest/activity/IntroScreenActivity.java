package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.nomadnest.R;
import com.example.nomadnest.adapter.IntroDotPagerAdapter;
import com.example.nomadnest.databinding.ActivityIntroScreenBinding;

public class IntroScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityIntroScreenBinding binding;
    private int[] layouts;
    private TextView[] dots;
    private IntroDotPagerAdapter introDotPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign view to binding
        binding = ActivityIntroScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set click listener to all view
        initializeListener();

        // declare intro screen layouts
        layouts = new int[]{
                R.layout.intro_screen_1,
                R.layout.intro_screen_2,
                R.layout.intro_screen_3,
                R.layout.intro_screen_4,
        };

        // initiate pager adapter for dots
        introDotPagerAdapter = new IntroDotPagerAdapter(IntroScreenActivity.this,layouts);
        binding.introViewpager.setAdapter(introDotPagerAdapter);
        binding.introViewpager.addOnPageChangeListener(viewPagerPageChangeListener);
        // set bottom position(Initial 0)
        setBottomPosition(0);


    }

    private void initializeListener() {
        binding.btnNext.setOnClickListener(this);
        binding.btnSkip.setOnClickListener(this);
    }

    private void setBottomPosition(int currentPosition) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        binding.llDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPosition]);
            binding.llDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPosition].setTextColor(colorsActive[currentPosition]);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            setBottomPosition(position);
            if (position == layouts.length - 1) {
                binding.btnNext.setText(getString(R.string.lets_explore));
                binding.btnSkip.setVisibility(View.GONE);
                binding.btnNextView.setVisibility(View.GONE);
            } else {
                binding.btnNext.setText(getString(R.string.next));
                binding.btnSkip.setVisibility(View.VISIBLE);
                binding.btnNextView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private int getCurrentPosition(int i) {
        return binding.introViewpager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.btnNext.getId()){
            int current = getCurrentPosition(+1);
            if (current < layouts.length) {
                // move to next screen
                binding.introViewpager.setCurrentItem(current);
            } else {
                goToHomeScreen();
            }
        }else if(v.getId() == binding.btnSkip.getId()){
            goToHomeScreen();
        }
    }

    private void goToHomeScreen() {
        startActivity(new Intent(IntroScreenActivity.this,HomeActivity.class));
        finish();
    }

}