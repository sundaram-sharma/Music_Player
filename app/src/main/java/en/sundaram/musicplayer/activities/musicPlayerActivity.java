package en.sundaram.musicplayer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import en.sundaram.musicplayer.R;
import en.sundaram.musicplayer.model.audioModel;
import en.sundaram.musicplayer.myMediaPlayer;

public class musicPlayerActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    TextView titleTv,currentTimeTv,totalTimeTv;
    SeekBar seekBar;
    Button imageButton;
    ImageView pausePlay,nextBtn,previousBtn,musicIcon;
    ArrayList<audioModel> songsList;
    audioModel currentSong;
    MediaPlayer mediaPlayer = myMediaPlayer.getInstance();
    int x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_Time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.music_icon_big);
        relativeLayout = findViewById(R.id.player_background);
        //imageButton = findViewById(R.id.back_button1);

        titleTv.setSelected(true);




        songsList = (ArrayList<audioModel>) getIntent().getSerializableExtra("LIST");

        setResourcesWithMusic();

        musicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition()); //set the seekbar to the current time and value
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+"")); //provide current time in text

                    if(mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.ic_pause); //if playing then pause
                        musicIcon.setRotation(x++);
                    }else{
                        pausePlay.setImageResource(R.drawable.ic_play); //if pause then playing
                        musicIcon.setRotation(0);
                    }

                }
                new Handler().postDelayed(this,100); //each 100 ms it will check and update seekbar
            }
        });

        //when user wants to change the progress in the seek bar

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


/*        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("thes","aya");
                Intent intent = new Intent(MusicPlayerActivity.this,MainActivity.class);
                Log.d("thes","or gaya");
            }
        });*/

    }

    void setResourcesWithMusic(){
        currentSong = songsList.get(myMediaPlayer.currentIndex);

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(currentSong.getPath());
        byte [] data = mmr.getEmbeddedPicture();

        if(data != null){ //check if the data array provided for image is present
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            try { //try catch for exception
                musicIcon.setImageBitmap(bitmap); //if album art is present, set icon to that
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                //relativeLayout.setBackground(drawable);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
                {
                    
                }
                else
                {

                }
            }
            catch (Exception e)
            {

            }

        }
        else
        {
            musicIcon.setImageResource(R.drawable.ic_music_icon_big); //default image if no album art
        }



        titleTv.setText(currentSong.getTitle());

        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v-> pausePlay());
        nextBtn.setOnClickListener(v-> playNextSong());
        previousBtn.setOnClickListener(v-> playPreviousSong());

        playMusic();


    }


    private void playMusic(){

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playNextSong(){

        if(myMediaPlayer.currentIndex== songsList.size()-1)
            return;
        myMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void playPreviousSong(){
        if(myMediaPlayer.currentIndex== 0)
            return;
        myMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }


    public static String convertToMMSS(String duration){ //method to get time in millisecond
        Long millis  = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));

    }
}