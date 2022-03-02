package to.msn.wings.atmsimulator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SimpleDatabaseHelper extends SQLiteOpenHelper{
    static final private String DBNAME = "ATMdb.sqlite";
    static final private int VERSION = 1;

    SimpleDatabaseHelper(Context context){
        super(context,DBNAME,null,VERSION);
    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ATMAccount(" + "number TEXT PRIMARY KEY,  pass Integer, money LONG)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v,int new_v){
        db.execSQL("DROP TABLE IF EXISTS ATMAccount");
        onCreate(db);
    }
}
