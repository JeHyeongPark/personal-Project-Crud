package kr.co.crud.domain;

import lombok.Data;

@Data
public class FileVO {

    private int fno;
    private String parent;
    private String newName;
    private String oriName;
    private String del_yn;
}
