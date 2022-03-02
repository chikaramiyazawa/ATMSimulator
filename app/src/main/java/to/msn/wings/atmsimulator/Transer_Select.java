package to.msn.wings.atmsimulator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;

public class Transer_Select extends AppCompatActivity {
    AudioManager am;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transer_screen);

    }

    public void Trancer_Cash_onClick(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.Transer_Cash.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);

    }

    public void Transer_onClick(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.Transer.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);
    }

    public void onBack(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.MainActivity.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.back);
        mp.start();
        startActivity(i);
    }
}
