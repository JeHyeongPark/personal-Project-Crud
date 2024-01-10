package kr.co.crud.domain;

import lombok.Data;

@Data
public class UserVO {

    private String uid;
    private String pass;
    private String name;
    private String birth;
    private String hp;
    private String rdate;
    private String zip;
    private String addr1;
    private String addr2;
    private int grade;

}
