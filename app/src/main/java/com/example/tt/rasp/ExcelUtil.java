package com.example.tt.rasp;

import android.util.Log;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.realm.Realm;

import static com.example.tt.rasp.Constants.FRIDAY;
import static com.example.tt.rasp.Constants.MONDAY;
import static com.example.tt.rasp.Constants.SATURDAY;
import static com.example.tt.rasp.Constants.THURSDAY;
import static com.example.tt.rasp.Constants.TUESDAY;
import static com.example.tt.rasp.Constants.WEDNESDAY;


/**
 * Created by tt on 12.09.17.
 */

public class ExcelUtil {

    private Calendar mCalendar = Calendar.getInstance();
//    public int currentDay = mCalendar.get(Calendar.DAY_OF_WEEK);
    public static int currentDay = 4;
    private Realm mRealm;


    ExcelUtil() {
        mCalendar.setTime(new Date());
    }

    public void readFromExcel(String file) throws IOException {
        mRealm = Realm.getDefaultInstance();
        EdDay edDay = new EdDay();
        int evenWeek = mCalendar.get(Calendar.WEEK_OF_YEAR) % 2;
        Log.d("myLog", "четность:" + evenWeek);
        Log.d("myLog", file);
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("ИВТ-М-1-Д-2016-2");


        HashMap<Integer, Integer> weekMap = new HashMap<>(6);
        weekMap.put(MONDAY, 14);
        weekMap.put(TUESDAY, 40);
        weekMap.put(WEDNESDAY, 46);
        weekMap.put(THURSDAY, 62);
        weekMap.put(FRIDAY, 78);
        weekMap.put(SATURDAY, 94);

        int neededRow = weekMap.get(currentDay);
        XSSFRow row = myExcelSheet.getRow(neededRow);
        Log.d("myLog", "day:" + currentDay + " ,row: " + neededRow);

        Log.d("myLog", "What is this:" + row.getCell(0).getStringCellValue());
        Log.d("myLog", "What is this:" + row.getCell(1).getStringCellValue());
        Log.d("myLog", "What is this:" + row.getCell(2).getStringCellValue());


        edDay.setDay(row.getCell(0).getStringCellValue());


        for (int i = 0; i <= 7; i++) {
            if (myExcelSheet.getRow(neededRow + i * 2 + evenWeek).getCell(3).getStringCellValue().equals(""))
                continue;

            edDay.putLesson(myExcelSheet.getRow(neededRow + i * 2 + evenWeek).getCell(3).getStringCellValue(),
                    myExcelSheet.getRow(neededRow + i * 2).getCell(1).getStringCellValue(),
                    myExcelSheet.getRow(neededRow + i * 2 + evenWeek).getCell(4).getStringCellValue());
        }


        writeToBase(edDay);

        myExcelBook.close();
        for (Lesson lesson : edDay.getLessons()) {
            Log.d("myLog", edDay.getDay() + ": " + lesson.toString());
        }

        mRealm.close();
    }

    public void writeToBase(EdDay result){
        final EdDay edDay = result;
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // This will create a new object in Realm or throw an exception if the
                // object already exists (same primary key)
                // realm.copyToRealm(obj);

                // This will update an existing object with the same primary key
                // or create a new object if an object with no primary key = 42
                realm.copyToRealmOrUpdate(edDay);
            }
        });
    }
}
