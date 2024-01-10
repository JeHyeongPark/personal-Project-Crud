package kr.co.crud.dao;

import kr.co.crud.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {


    public void register(UserVO userVO);


}
