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
    public static EdDay readFromExcel(String file) throws IOException {
        EdDay edDay = new EdDay();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int evenWeek = calendar.get(Calendar.WEEK_OF_YEAR)%2;
        Log.d("myLog", "четность:" + evenWeek);
        Log.d("myLog", file);
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("ИВТ-М-1-Д-2016-2");


        HashMap<Integer, Integer> hashMap = new HashMap<>(6);
        hashMap.put(MONDAY, 14);
        hashMap.put(TUESDAY, 40);
        hashMap.put(WEDNESDAY, 46);
        hashMap.put(THURSDAY, 62);
        hashMap.put(FRIDAY, 78);
        hashMap.put(SATURDAY, 94);

        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
//        TODO: убрать в прод
        currentDay = 4;

        int neededRow = hashMap.get(currentDay);
        XSSFRow row = myExcelSheet.getRow(neededRow);
        Log.d("myLog", "day:" + currentDay + " ,row: " + neededRow);

        String day = "day";

        Log.d("myLog", "What is this:" + row.getCell(0).getStringCellValue());
        Log.d("myLog", "What is this:" + row.getCell(1).getStringCellValue());
        Log.d("myLog", "What is this:" + row.getCell(2).getStringCellValue());



        edDay.setDay(row.getCell(0).getStringCellValue());


        for (int i = 0; i <= 7; i++) {
            if (myExcelSheet.getRow(neededRow + i * 2 + evenWeek).getCell(3).getStringCellValue().equals("")) continue;

            edDay.putLesson(myExcelSheet.getRow(neededRow + i * 2 + evenWeek).getCell(3).getStringCellValue(),
                    myExcelSheet.getRow(neededRow + i * 2).getCell(1).getStringCellValue(),
                    myExcelSheet.getRow(neededRow + i * 2 + evenWeek).getCell(4).getStringCellValue());
        }



        myExcelBook.close();
        for(Lesson lesson: edDay.getLessons()) {
            Log.d("myLog", edDay.getDay() + ": " + lesson.toString());
        }


        return edDay;
    }
}
