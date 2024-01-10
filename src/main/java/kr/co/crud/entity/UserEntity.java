package kr.co.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="crud_user")
public class UserEntity {

    // JPA에서 테이블과 매핑되는 객체
    @Id
    private String uid;
    private String pass;
    private String name;
    private String email;
    private String birth;
    private String hp;
    private String rdate;
    private String zip;
    private String addr1;
    private String addr2;
    private int grade;

}
