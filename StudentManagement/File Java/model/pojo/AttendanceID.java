package model.pojo;

import java.io.Serializable;
import java.util.Date;

public class AttendanceID implements Serializable {
    
    private int id;
    private Date checkedDate;
    
    public AttendanceID() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }
    
}
