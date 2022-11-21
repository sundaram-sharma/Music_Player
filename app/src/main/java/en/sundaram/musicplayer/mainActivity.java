package en.sundaram.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

import en.sundaram.musicplayer.ui.equalizer.equalizerFragment;
import en.sundaram.musicplayer.ui.splash.splashScreen;
import en.sundaram.musicplayer.ui.theme.themefragment;

public class mainActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    TextView noMusicTextView;
    ArrayList<audioModel> songsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true); //highlight the item in navigation drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.mainHome:
                        Intent intent = new Intent(mainActivity.this, mainActivity.class); //intent from splash ac
                        startActivity(intent);
                        getFragmentManager().popBackStack();
                        finish();

                        ;break;
                    case R.id.themes:
                        replaceFragment(new themefragment());
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.equalizer:
                        Toast.makeText(mainActivity.this, "Synch is Clicked",Toast.LENGTH_SHORT).show();
                        replaceFragment(new equalizerFragment());
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.widget:
                        Toast.makeText(mainActivity.this, "Widget is Clicked",Toast.LENGTH_SHORT).show();
                        replaceFragment(new themefragment());
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.settings:
                        getFragmentManager().popBackStack();
                        replaceFragment(new SettingsFragment());


                        break;
                    case R.id.shareApp:
                        Toast.makeText(mainActivity.this, "Share is clicked",Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();

                        break;
                    case R.id.rateUs:
                        Toast.makeText(mainActivity.this, "Rate us is Clicked",Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();

                        break;
                    default:
                        return true;

                }
                return true;
            }
        });





        recyclerView = findViewById(R.id.recycler_view);
        noMusicTextView = findViewById(R.id.no_song_text);

        if(checkPermission() == false){
            requestPermission();


            //return;
        }



        Log.d("hello","bahar");
        String[] projection = { //
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext()){
            audioModel songData = new audioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(songData.getPath()).exists())
                songsList.add(songData);

        }


        if(songsList.size()==0){
            Log.d("hello","if statement");
            noMusicTextView.setVisibility(View.VISIBLE);
            Toast.makeText(this,"No audio file found",Toast.LENGTH_SHORT).show();
        }else{
            Log.d("hello","else statement");
            //recyclerview
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new musicListAdapter(songsList,getApplicationContext()));
        }

    }

    boolean checkPermission(){ //the method will check for the permission
        int result = ContextCompat.checkSelfPermission(mainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE); //store the permission result in result
        if(result == PackageManager.PERMISSION_GRANTED)
        {
            return true; //return true to the method
        }
        else
        {
            return false; //return false to the method
        }
    }

    void requestPermission(){ //this method will ask for the permission
        if(ActivityCompat.shouldShowRequestPermissionRationale(mainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(mainActivity.this,"READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTINGS",Toast.LENGTH_SHORT).show();
        }else {
            ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        }
    }

    @Override
    protected void onResume() { //update list when onResume
        super.onResume();
        if(recyclerView!=null){
            recyclerView.setAdapter(new musicListAdapter(songsList,getApplicationContext()));
        }
    }

    void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager(); //make fragManager object
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //begin trasnaction
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}