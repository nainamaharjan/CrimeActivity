package com.example.testfeb.crimeactivity;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeDatabaseSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDatabaseSchema.CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeDatabaseSchema.CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDatabaseSchema.CrimeTable.Cols.SOLVED));
        String suspect =getString(getColumnIndex(CrimeDatabaseSchema.CrimeTable.Cols.SUSPECT));


        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setCrimeSuspect(suspect);
        return crime;
    }
}
