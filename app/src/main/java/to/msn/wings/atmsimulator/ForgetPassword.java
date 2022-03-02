package to.msn.wings.atmsimulator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends  AppCompatActivity{
    private SimpleDatabaseHelper helper = null;
    private EditText txtnumber = null;
    private EditText txtpass = null;
    AudioManager am;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        helper = new SimpleDatabaseHelper(this);
        txtnumber = findViewById(R.id.txtnumber);
        txtpass = findViewById(R.id.txtpass);

    }

    public void onAuthentication(View view) {
        String[] cols = {"number", "pass", "money"};
        String[] params = {txtnumber.getText().toString()};
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.query("ATMAccount", cols, "number = ?" ,params,  null, null,null,null)) {
            if (cs.moveToFirst()) {
                txtpass.setText(cs.getString(1));
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                mp = MediaPlayer.create(this, R.raw.sound1);
                mp.start();
            } else {
                Toast.makeText(this, "データがありません。", Toast.LENGTH_SHORT).show();
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                mp = MediaPlayer.create(this, R.raw.back);
                mp.start();
            }
        }
    }
    public void onBack(View view){
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.back);
        mp.start();
        Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
        startActivity(i);
    }
}
