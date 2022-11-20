package en.sundaram.musicplayer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

import en.sundaram.musicplayer.R;

public class themeGVAdapter extends ArrayAdapter<themeModel> {

    public themeGVAdapter(@NonNull Context context, ArrayList<themeModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        themeModel themeModel = getItem(position);
        TextView themeTV = listitemView.findViewById(R.id.idTVCourse);
        ImageView themeIV = listitemView.findViewById(R.id.idIVcourse);

        themeTV.setText(themeModel.getTheme_name());
        themeIV.setImageResource(themeModel.getImgId());
        return listitemView;
    }
}