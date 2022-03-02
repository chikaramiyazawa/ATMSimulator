package to.msn.wings.atmsimulator;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Transer_Cash extends AppCompatActivity {
    private SimpleDatabaseHelper helper = null;
    private EditText txtnumber = null;
    private EditText txtpass = null;
    private EditText txtmoney = null;
    private EditText txtcash = null;
    AudioManager am;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transer_cash);
        helper = new SimpleDatabaseHelper(this);
        txtnumber = findViewById(R.id.txtnumber);
        txtpass = findViewById(R.id.txtpass);
        txtmoney = findViewById(R.id.txtmoney);
        txtcash = findViewById(R.id.txtcash);
        txtpass.setVisibility(View.INVISIBLE);
        txtmoney.setVisibility(View.INVISIBLE);

    }

    public void onTrancer_Cash(View view) {

            String[] cols = {"number", "pass", "money"};
            String[] params = {txtnumber.getText().toString()};
            try (SQLiteDatabase db = helper.getReadableDatabase();
                 Cursor cs = db.query("ATMAccount", cols, "number = ?", params, null, null, null,null)) {
                if (cs.moveToFirst()) {
                    txtpass.setText(cs.getString(1));
                    txtmoney.setText(cs.getString(2));
                    int money = Integer.parseInt(txtmoney.getText().toString());
                    try {
                        int cash = Integer.parseInt(txtcash.getText().toString());
                        if (cash < 0) {
                            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                            mp = MediaPlayer.create(this, R.raw.back);
                            mp.start();
                            Toast.makeText(this, "お振込み金額を入力してください。",
                                    Toast.LENGTH_SHORT).show();
                        }
                        int result = money + cash;
                        if (result >= 999999999) {
                            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                            mp = MediaPlayer.create(this, R.raw.back);
                            mp.start();
                            Toast.makeText(this, "一定以上の金額を超えています。",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            try (SQLiteDatabase db2 = helper.getWritableDatabase()) {
                                ContentValues cv = new ContentValues();
                                cv.put("number", txtnumber.getText().toString());
                                cv.put("pass", txtpass.getText().toString());
                                cv.put("money", result);
                                db2.insertWithOnConflict("ATMAccount", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
                                Toast.makeText(this, "お振込完了しました", Toast.LENGTH_SHORT).show();
                                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                                mp = MediaPlayer.create(this, R.raw.sound2);
                                mp.start();
                                Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
                                startActivity(i);

                            }
                        }

                    } catch (NumberFormatException e) {
                        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                        mp = MediaPlayer.create(this, R.raw.back);
                        mp.start();
                        Toast.makeText(this, "お振込み金額を入力してください。",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                        Toast.makeText(this, "データがありません。", Toast.LENGTH_SHORT).show();
                        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                        mp = MediaPlayer.create(this, R.raw.back);
                        mp.start();}

                }
            }


        public void onBack (View view){
            Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
            mp = MediaPlayer.create(this, R.raw.back);
            mp.start();
            startActivity(i);
        }
    }





