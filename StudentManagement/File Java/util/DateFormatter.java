/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;

public class DateFormatter extends JFormattedTextField.AbstractFormatter {
    public interface Callback {
        void receiveDate(Date date);
    }
    
    private static final String TIME_PATTERN = "HH:mm";
    
    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);
    
    private static final String FULL_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    
    private static final SimpleDateFormat fullTimeFormatter = new SimpleDateFormat(FULL_TIME_PATTERN);
    
    private Callback callback;
    
    public DateFormatter(Callback callback) {
        this.callback = callback;
    }
    
    public static String fullTimeAsStringOf(Date date) {
        return fullTimeFormatter.format(date);
    }
    
    public static String timeAsStringOf(Date date) {
        return timeFormatter.format(date);
    }
    
    public static String stringOf(Date date) {
        return dateFormatter.format(date);
    }
    
    public static Date fullTimeOf(String dateAsStr) {
        try {
            return fullTimeFormatter.parse(dateAsStr);
        } catch (ParseException e) {
            System.out.println("Error in DateFormatter: " + e.getMessage());
        }
        
        return null;
    }
    
    public static Date valueOf(String dateAsStr) {
        try {
            return dateFormatter.parse(dateAsStr);
        } catch (ParseException e) {
            System.out.println("Error in DateFormatter: " + e.getMessage());
        }
        
        return null;
    }
    
    private Date displayedDate;
    
    public void setChangedListener(Callback callback) {
        this.callback = callback;
        
        if (callback != null) {
            callback.receiveDate(displayedDate);
        }
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        Calendar calendar;
        if (value == null) {
            calendar = Calendar.getInstance();
            calendar.setTime(new Date());
        } else {
            calendar = (Calendar) value;
        }
        
        displayedDate = calendar.getTime();
        
        if (callback != null) {
            callback.receiveDate(displayedDate);
        }

        return dateFormatter.format(displayedDate);
    }
}
