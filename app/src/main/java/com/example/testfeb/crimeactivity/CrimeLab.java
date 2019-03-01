package com.example.testfeb.crimeactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
    private Context context_cl;
    private SQLiteDatabase dataBase;
//    private List<Crime> crimes;

    public static CrimeLab get(Context context) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }
    public CrimeLab(Context context) {
//        crimes = new ArrayList<>();
//        int i;
//        for(i=1 ; i<=100; i++){
//            Crime crime = new Crime();
//            crime.setTitle("Crime#" + i);
//            crime.setSolved(i%2 == 0);
//            crimes.add(crime);
//        }

        context_cl= context.getApplicationContext();
        dataBase = new CrimeBaseHelper(context_cl).getWritableDatabase();

    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        dataBase.insert(CrimeDatabaseSchema.CrimeTable.NAME, null, values);
//        crimes.add(c);
    }
    public List<Crime> getCrimes() {
//        return crimes;
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
//        for (Crime crime : crimes) {
//            if (crime.getId().equals(id)) {
//                return crime;
//            }
//        }
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeDatabaseSchema.CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        dataBase.update(CrimeDatabaseSchema.CrimeTable.NAME, values,
                CrimeDatabaseSchema.CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }
    public int deleteCrime(UUID crimeId)
    {
        String uuidString = crimeId.toString();

        return      dataBase.delete(CrimeDatabaseSchema.CrimeTable.NAME,
               CrimeDatabaseSchema.CrimeTable.Cols.UUID + "= ?",new String[]{uuidString});
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeDatabaseSchema.CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeDatabaseSchema.CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeDatabaseSchema.CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeDatabaseSchema.CrimeTable.Cols.SOLVED, crime.getSolved() ? 1 : 0);
        values.put(CrimeDatabaseSchema.CrimeTable.Cols.SUSPECT, crime.getCrimeSuspect());
        return values;
    }
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = dataBase.query(
                CrimeDatabaseSchema.CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }


}
