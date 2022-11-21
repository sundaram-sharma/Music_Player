package en.sundaram.musicplayer.fragment.theme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import en.sundaram.musicplayer.R;
import en.sundaram.musicplayer.adapter.themeGVAdapter;
import en.sundaram.musicplayer.model.themeModel;


public class themefragment extends Fragment {

    GridView themeGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //1. Event
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //2. Event
        // Inflate the layout for this fragment





        return inflater.inflate(R.layout.fragment_theme_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //3. Event

        super.onViewCreated(view, savedInstanceState);

        themeGridView = view.findViewById(R.id.idGVthemes);
        ArrayList<themeModel> themeModelArrayList = new ArrayList<themeModel>();


        themeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Toast.makeText(getActivity(), "Storm theme is selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "City1 theme is selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Forest theme is selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "City2 theme is selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "Scene theme is selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getActivity(), "Animated theme is selected",Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });


        themeModelArrayList.add(new themeModel("Storm", R.drawable.ic_background1));
        themeModelArrayList.add(new themeModel("city1", R.drawable.ic_background2));
        themeModelArrayList.add(new themeModel("Forest", R.drawable.ic_background3));
        themeModelArrayList.add(new themeModel("city2", R.drawable.ic_background4));
        themeModelArrayList.add(new themeModel("Scene", R.drawable.ic_background5));
        themeModelArrayList.add(new themeModel("Visualizer", R.drawable.ic_backgroung_giffy));


        themeGVAdapter adapter = new themeGVAdapter(getActivity(), themeModelArrayList);
        themeGridView.setAdapter(adapter);

    }
}
