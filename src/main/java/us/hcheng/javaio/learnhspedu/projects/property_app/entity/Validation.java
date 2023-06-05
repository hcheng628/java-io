package us.hcheng.javaio.learnhspedu.projects.property_app.entity;

import lombok.Data;

@Data
public class Validation {
    int maxLen;
    boolean allowEmpty;

    public Validation(int maxLen, boolean allowEmpty) {
        this.maxLen = maxLen;
        this.allowEmpty = allowEmpty;
    }

}
