package kr.co.crud.domain;

import lombok.Data;

@Data
public class CommentVO {

    private int cno;
    private String parent;
    private String comment;
    private String rdate;
    private String uid;
    private String del_yn;
    private String no;
    private String name;
}
