package com.example.sqlite;

import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String STUDENT_NAME = "STUDENTName";
    public static final String STUDENT_AGE = "STUDENTAge";
    public static final String ACTIVE_STUDENT = "ActiveSTUDENT";
    public static final String STUDENT_ID = "STUDENTID";
    public static final String STUDENT_TABLE = "StudentTable";

    public DBHelper(@Nullable Context context) {
        super(context, "studentDB.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableSTatementOne = "CREATE TABLE CustTable(STUDENTID Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME_FIRST + " Text, STUDENTAge Int, ActiveSTUDENT BOOL) ";
        String createTableSTatement = "CREATE TABLE " + STUDENT_TABLE + "(" + STUDENT_ID + " Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME + " Text, " + STUDENT_AGE + " Int, " + ACTIVE_STUDENT + " BOOL) ";
        db.execSQL(createTableSTatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        onCreate(db);
    }

    public void addStudent(StudentModel STUDENTModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(STUDENT_NAME, STUDENTModel.getName());
        cv.put(STUDENT_AGE, STUDENTModel.getAge());
        cv.put(ACTIVE_STUDENT, STUDENTModel.isActive());
        db.insert(STUDENT_TABLE, null, cv);
        db.close();
    }

    public ArrayList<StudentModel> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + STUDENT_TABLE, null);
        ArrayList<StudentModel> studentArrayList = new ArrayList<>();
        if (cursorCourses.moveToFirst()) {
            do {
                studentArrayList.add(new StudentModel(cursorCourses.getInt(0),cursorCourses.getString(1),
                        cursorCourses.getInt(2),
                        cursorCourses.getInt(3) == 1 ? true : false));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return studentArrayList;
    }

    public boolean deleteStudent(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(STUDENT_TABLE, STUDENT_ID + "=" + id, null) > 0;
    }

    public boolean updateStudent(StudentModel studentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_NAME, studentModel.getName()); //These Fields should be your String values of actual column names
        cv.put(STUDENT_AGE, studentModel.getAge());
        cv.put(ACTIVE_STUDENT, studentModel.isActive());
        return db.update(STUDENT_TABLE, cv, STUDENT_ID + "=" + studentModel.getId(),null) > 0;
    }
}