package com.jeffersondeguzman.classattendance.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.jeffersondeguzman.classattendance.ui.attendance.AttendanceModel;
import com.jeffersondeguzman.classattendance.ui.classUI.ClassModel;
import com.jeffersondeguzman.classattendance.ui.students.AttendanceDateModel;
import com.jeffersondeguzman.classattendance.ui.students.StudentsModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.jeffersondeguzman.classattendance/databases/";

    private static String DB_NAME = "ClassAttendance.db";

    private SQLiteDatabase myDatabase;

    private final Context myContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
            Log.i("DatabaseHelper", "DB is existing. NOT COPYING");
        } else {
            //By calling this method an empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("DatabaseHelper", e.getMessage());
                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                File dbFile = myContext.getDatabasePath(DB_NAME);
                myPath = dbFile.getPath();
                Log.i("DatabaseHelper", "here1: " + myPath);

            } else {
                myPath = DB_PATH + DB_NAME;
                Log.i("DatabaseHelper", "here2: " + myPath);
            }
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (SQLiteException e) {

            //database does't exist yet.
            Log.e("DatabaseHelper", e.getLocalizedMessage());

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        Log.i("DatabaseHelper", "DB is not existing. COPYING");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db

        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    @Override
    public synchronized void close() {
        if (myDatabase != null)
            myDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE tblClassroom (" +
                "classID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "classSubject TEXT, " +
                "className TEXT)";

        String createTableQuery1 = "CREATE TABLE tblAttendance (" +
                "attendanceID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "attendanceName TEXT, " +
                "classID INTEGER, " +
                "FOREIGN KEY (classID) REFERENCES tblClassroom(classID))";

        String createTableQuery2 = "CREATE TABLE tblStudents (" +
                "studentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "studentAbsents INTEGER, " +
                "studentFirstName TEXT, " +
                "studentLastName TEXT, " +
                "studentDate TEXT, " +
                "studentStat INTEGER, " +
                "attendanceID INTEGER, " +
                "FOREIGN KEY (attendanceID) REFERENCES tblAttendance(attendanceID))";

        String createTableQuery3 = "CREATE TABLE tblAttendanceDate (" +
                "attendanceDateID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "attendanceDate TEXT," +
                "studentID INTEGER, " +
                "FOREIGN KEY (studentID) REFERENCES tblClassroom(studentID))";

        db.execSQL(createTableQuery);
        db.execSQL(createTableQuery1);
        db.execSQL(createTableQuery2);
        db.execSQL(createTableQuery3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<ClassModel> getAllClass(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<ClassModel> data = new ArrayList<>();

        Cursor cursor;

        try{
            cursor = db.query("tblClassroom", null, null, null, null, null,null);
            while(cursor.moveToNext()){
                ClassModel classs = new ClassModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                data.add(classs);
            }
            Log.i("DatabaseHelper", "testing2" + data);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;
    }
    public long addClass(String Name, String Subject){
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("className", Name);
        cv.put("classSubject", Subject);
        try {
            result = db.insert("tblClassroom", null, cv);
        } catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return result;
    }
    public void deleteClass(int classID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        db.delete("tblClassroom", "classID = ?", new String[]{"" + classID});
    }

    public List<AttendanceModel> getAllAttendance(int classID){
        SQLiteDatabase db = this.getWritableDatabase();
        List<AttendanceModel> data = new ArrayList<>();

        Cursor cursor;

        try{
            cursor = db.query("tblAttendance", null, "classID = ?", new String[]{""+classID}, null, null,null);
            while(cursor.moveToNext()){
                AttendanceModel atte = new AttendanceModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                data.add(atte);
            }
            Log.i("DatabaseHelper", "" + data);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;
    }
    public long addAttendance(String Name, int classID){
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("attendanceName", Name);
        cv.put("classID", classID);
        try {
            result = db.insert("tblAttendance", null, cv);
        } catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return result;
    }

    public void deleteAttendance(int attendanceID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        db.delete("tblAttendance", "attendanceID = ?", new String[]{"" + attendanceID});
    }

    public List<StudentsModel> getAllStudents(int attendanceID){
        SQLiteDatabase db = this.getWritableDatabase();
        List<StudentsModel> data = new ArrayList<>();

        Cursor cursor;

        try{
            cursor = db.query("tblStudents", null, "attendanceID = ?", new String[]{""+attendanceID}, null, null,null);
            while(cursor.moveToNext()){
                StudentsModel studs = new StudentsModel(cursor.getInt(0),cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
                data.add(studs);
            }
            Log.i("DatabaseHelper", "testing3" + data);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;
    }
    public StudentsModel getStudent(int studentID){
        SQLiteDatabase db = this.getWritableDatabase();
        StudentsModel std = null;

        Cursor cursor;

        try{
            cursor = db.query("tblStudents", null, "studentID = ?", new String[]{"" + studentID}, null, null,null);
            cursor.moveToFirst();
            std = new StudentsModel(cursor.getInt(0),cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
            Log.i("DatabaseHelper", "" + std);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return std;
    }
    public StudentsModel getStudentStat(int studentID){
        SQLiteDatabase db = this.getWritableDatabase();
        StudentsModel std = null;

        Cursor cursor;

        try{
            cursor = db.query("tblStudents", null, "studentID = ?", new String[]{"" + studentID}, null, null,null);
            cursor.moveToFirst();
            std = new StudentsModel(cursor.getInt(0),cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
            Log.i("DatabaseHelper", "" + std);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return std;
    }
    public long addStudent(String FirstName, String LastName, int attendanceID){
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("studentFirstName", FirstName);
        cv.put("studentLastName", LastName);
        cv.put("attendanceID", attendanceID);
        cv.put("studentStat", 1);
        try {
            result = db.insert("tblStudents", null, cv);
        } catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return result;
    }
    public void updateStudentStat(int studentID, int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("studentStat", value);
        try {
            db.update("tblStudents", cv, "studentID = ?", new String[]{"" + studentID});
        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
    }

    public int updateStudent(int studentID, String firstName, String lastName){
        int result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("studentFirstName", firstName);
        cv.put("studentLastName", lastName);
        try {
            result = db.update("tblStudents", cv, "studentID = ?", new String[]{"" + studentID});
        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return result;
    }
    public List<StudentsModel> getStudentsWithMatches(String searchText){
        SQLiteDatabase db = this.getWritableDatabase();
        List<StudentsModel> data = new ArrayList<>();

        Cursor cursor;

        try{
            cursor = db.query("tblStudents", null,"studentFirstName LIKE ? OR studentLastName LIKE ?",new String[]{"%"+ searchText + "%", "%"+ searchText + "%"},null, null,null);

            while(cursor.moveToNext()){
                StudentsModel std = new StudentsModel(cursor.getInt(0),cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
                data.add(std);
            }

            Log.i("DatabaseHelper", "" + data);

        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;

    }
    public int updateDate(int studentID, String dateAttendance){
        int result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("studentDate", dateAttendance);
        try {
            result = db.update("tblStudents", cv, "studentID = ?", new String[]{"" + studentID});
        }catch (Exception e){
            Log.e("DatabaseHelperzz", "" + e.getLocalizedMessage());
        }
        return result;
    }
    public long addAttendanceDate(String attendanceDate, int studentID){
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("attendanceDate", attendanceDate);
        cv.put("studentID", studentID);
        try {
            result = db.insert("tblAttendanceDate", null, cv);
        } catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return result;
    }
    public AttendanceDateModel getAttendanceDate(int studentID){
        SQLiteDatabase db = this.getWritableDatabase();
        AttendanceDateModel data = null;
        Cursor cursor;

        try{
            cursor = db.query("tblAttendanceDate", null, "studentID = ?", new String[]{"" + studentID}, null, null,null);
            cursor.moveToFirst();
            data = new AttendanceDateModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            Log.i("DatabaseHelper", "" + data);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return data;
    }

    public List<AttendanceDateModel> getAllAttendanceDate(int studentID){
        SQLiteDatabase db = this.getWritableDatabase();
        List<AttendanceDateModel> data = new ArrayList<>();

        Cursor cursor;

        try{
            cursor = db.query("tblAttendanceDate", null, "studentID = ?", new String[]{""+studentID}, null, null,null);
            while(cursor.moveToNext()){
                AttendanceDateModel studs = new AttendanceDateModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                data.add(studs);
            }
            Log.i("DatabaseHelperadwd", "testing3" + data);
        }catch(Exception e){
            Log.e("DatabaseHelperfff", "" + e.getLocalizedMessage());
        }

        return data;
    }

    public void updateAllStudentStat(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("studentStat", 1);
        try {
            db.update("tblStudents", cv, null, null);
        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
    }
}
