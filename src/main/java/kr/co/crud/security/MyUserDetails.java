package kr.co.crud.security;

import kr.co.crud.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * AuthenticationManager => UserDetails
 * - 생성된 Authentication 객체를 검증 및 인증을 수행
 * */
@Data
@Builder
public class MyUserDetails implements UserDetails {

    /*
     * 직렬화(Serializable)
     * - 자바의 객체를 바이트의 배열로 변환하여 DB에 저장
     * */
    private static final long serialVersionUID = 1L;

    @Autowired
    private UserEntity user;

    /*
     * getAuthorities()
     * - 사용자가 가지고 있는 권한을 반환하는 메서드
     * - 반환타입: Collection<? extends GrantedAuthority>
     * */
    // <? extends GrantedAuthority>: 와일드카드('?')를 사용하여 GrantedAuthority가 확장한 타입이 될 수 있음을 명시
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // GrantedAuthority 객체들을 저장하기 위한 리스트 생성
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 권한을 나타내는 SimpleGrantedAuthority 객체를 생성하여 리스트에 추가
        // 일반적으로 Spring Security의 규약에 따라 "ROLE_" 접두어를 붙여 권한 생성
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getGrade()));

        return authorities;
    }

    /*
     * getUsername()
     * - 사용자의 아이디를 반환하는 메서드
     * */
    @Override
    public String getUsername() {
        return user.getUid();
    }

    /*
     * getPassword()
     * - 사용자의 비밀번호를 반환하는 메서드
     * */
    @Override
    public String getPassword() {
        return user.getPass();
    }



    public String getNickname() {
        return user.getName();
    }



    /* 그외 */
    // 계정 만료 여부 (true: 만료X, false: 만료)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부 (true: 잠김X, false: 잠김)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 비밀번호 만료 여부 (true: 만료X, false: 만료)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 (true: 활성화, false: 비활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
