package model.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Student implements Serializable {
    
    private String code;
    private String name;
    private int schoolYear;
    private String faculty;
    private Date birthday;
    
    public Student() {}

    public Student(String code, String name, int schoolYear, String faculty, Date birthday) {
        this.code = code.toUpperCase();
        this.name = name.toUpperCase();
        this.schoolYear = schoolYear;
        this.faculty = faculty;
        this.birthday = birthday;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student other = (Student)obj;
            if (this.code.equalsIgnoreCase(other.code)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.code);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.schoolYear);
        hash = 17 * hash + Objects.hashCode(this.birthday);
        return hash;
    }
    
}
