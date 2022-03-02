package to.msn.wings.atmsimulator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class Registration extends AppCompatActivity {
    TextView textView;
    private SimpleDatabaseHelper helper = null;
    private EditText txtnumber = null;
    private EditText txtpass = null;
    private EditText txtmoney = null;
    AudioManager am;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);
        helper = new SimpleDatabaseHelper(this);
        txtnumber = findViewById(R.id.txtnumber);
        txtpass = findViewById(R.id.txtpass);
        txtmoney = findViewById(R.id.txtmoney);


    }
    public void onRegistration(View view) {
        int money = Integer.parseInt(txtmoney.getText().toString());
        if (money < 1000 ) {
            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
            mp = MediaPlayer.create(this,R.raw.back);
            mp.start();
            Toast.makeText(this, "入金は1000円からです。",
                    Toast.LENGTH_SHORT).show();
        } else {
            try (SQLiteDatabase db = helper.getWritableDatabase()) {
                ContentValues cv = new ContentValues();
                cv.put("number", txtnumber.getText().toString());
                cv.put("pass", txtpass.getText().toString());
                cv.put("money", txtmoney.getText().toString());
                db.insertWithOnConflict("ATMAccount", null, cv, SQLiteDatabase.CONFLICT_ROLLBACK);
                Toast.makeText(this, "登録に成功しました。",
                        Toast.LENGTH_SHORT).show();
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
                mp = MediaPlayer.create(this,R.raw.sound2);
                mp.start();
                Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
                startActivity(i);
            } catch (SQLiteException e) {
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
                mp = MediaPlayer.create(this,R.raw.back);
                mp.start();
                Toast.makeText(this, "既存のデータが存在するため登録できません。",
                        Toast.LENGTH_SHORT).show();
            }
        }
        }
    public void onBack(View view){
        Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.back);
        mp.start();
        startActivity(i);
    }




}





