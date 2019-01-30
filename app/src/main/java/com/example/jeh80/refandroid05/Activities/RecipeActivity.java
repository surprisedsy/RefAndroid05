package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jeh80.refandroid05.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class RecipeActivity extends AppCompatActivity {

    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        bottomBarClicked();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent homeIntent = new Intent(RecipeActivity.this, TempActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void bottomBarClicked()
    {
        bottomBar = (BottomBar) findViewById(R.id.bottombar3);
        bottomBar.setDefaultTabPosition(3);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if(tabId == R.id.btm_home)
                {
                    Intent homeIntent = new Intent(RecipeActivity.this, TempActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                if(tabId == R.id.btm_itemlist)
                {
                    Intent itemIntent = new Intent(RecipeActivity.this, ItemListActivity.class);
                    startActivity(itemIntent);
                    finish();
                }
                if(tabId == R.id.btm_photo)
                {
                    Intent photoIntent = new Intent(RecipeActivity.this, PhotoActivity.class);
                    startActivity(photoIntent);
                    finish();
                }
            }
        });
    }
}
