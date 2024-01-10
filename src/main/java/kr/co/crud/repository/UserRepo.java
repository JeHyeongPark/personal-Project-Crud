package kr.co.crud.repository;

import kr.co.crud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Spring Data JPA를 사용하여 데이터베이스와 상호 작용하는 데 사용
 * 사용자 엔터티(UserEntity)와 관련된 데이터베이스 작업을 수행하는 데 사용
 * */

// <UserEntity, String> : <사용자 정보를 나타내는 엔터티, 해당 엔터티의 주키(PK) 타입>
public interface UserRepo extends JpaRepository<UserEntity, String> {

    // 사용자 아이디(uid)를 기준으로 데이터베이스에서 해당 아이디의 레코드 개수를 조회하는 메서드
    public int countByUid(String uid);

}
