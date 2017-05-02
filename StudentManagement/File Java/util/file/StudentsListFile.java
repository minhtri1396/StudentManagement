package util.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import model.pojo.Student;
import util.DateFormatter;

public class StudentsListFile {
    
    public static Student[] getStudentsFrom(String path) {
        ArrayList<Student> students = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), "utf-8"))) {
            String line = br.readLine();
            if (line != null) {
                String[] columnNames = line.split(", ");
                if (columnNames.length == TemplateFile.COLUMN_NAME.length) {
                    for (int i = 0; i < columnNames.length; ++i) {
                        if (!columnNames[i].equalsIgnoreCase(TemplateFile.COLUMN_NAME[i])) {
                            return null;
                        }
                    }
                    
                    String[] columnValues;
                    boolean isCanAdd;
                    while ((line = br.readLine()) != null) {
                        columnValues = line.split(", ");
                        isCanAdd = true;
                        for (int i = 0; i < columnValues.length; ++i) {
                            if (columnValues[i].trim().length() == 0) {
                                isCanAdd = false;
                                break;
                            }
                        }
                        if (isCanAdd) {
                            students.add(new Student(
                                    columnValues[0],
                                    columnValues[1],
                                    Integer.parseInt(columnValues[3]),
                                    "CÔNG NGHỆ THÔNG TIN",
                                    DateFormatter.valueOf(columnValues[2])
                            ));
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        
        return students.toArray(new Student[0]);
    }
    
}
