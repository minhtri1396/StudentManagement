package model.pojo;

import java.io.Serializable;
import java.util.Objects;

public class SubjectID implements Serializable {
    private String code;
    
    private int nameId;
    
    public SubjectID() {}

    public SubjectID(String code, int nameId) {
        this.code = code;
        this.nameId = nameId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubjectID) {
            SubjectID other = (SubjectID)obj;
            return (nameId == other.nameId) && (code.equalsIgnoreCase(other.code));
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.code);
        hash = 71 * hash + this.nameId;
        return hash;
    }
    
}
