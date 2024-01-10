package kr.co.crud.service;


import kr.co.crud.dao.UserDAO;
import kr.co.crud.domain.UserVO;
import kr.co.crud.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDAO dao;

    public void register(UserVO userVO) {

        userVO.setPass(encoder.encode(userVO.getPass()));

        dao.register(userVO);

    }




}


