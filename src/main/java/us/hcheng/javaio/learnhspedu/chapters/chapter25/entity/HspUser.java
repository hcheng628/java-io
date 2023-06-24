package us.hcheng.javaio.learnhspedu.chapters.chapter25.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1. Member variables' order does not matter
 *
 * 2. If Member variables are NOT matched to columns in DB while doing query,
 *  they are going to be null values
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HspUser {
    private int id;
    private String name;
    private String pwd;
}
