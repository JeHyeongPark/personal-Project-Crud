package kr.co.crud.security;

import kr.co.crud.entity.UserEntity;
import kr.co.crud.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * UserDetailsService
 * - UserDetails 객체를 사용해 인증 및 권한 부여
 * */
@Service
@Slf4j
public class SecurityUserService implements UserDetailsService {

    // 레포지토리를 통해 데이터베이스에서 사용자 정보를 조회
    @Autowired
    private UserRepo repo;

    // loadUserByUsername: 사용자의 아이디(username)을 기반으로 사용자 정보를 가져온다
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.warn("SecurityUserService loadUserByUsername");

        // 데이터베이스에서 사용자 정보를 조회
        UserEntity user = repo.findById(username).get();

        if(user == null) {
            throw  new UsernameNotFoundException(username);
        }

        // 조회한 사용자 정보로 UserDetails 객체 생성
        UserDetails myUser = MyUserDetails.builder()
                .user(user)
                .build();

        return myUser;
    }

}
