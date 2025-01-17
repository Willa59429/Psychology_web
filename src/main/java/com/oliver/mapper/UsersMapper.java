package com.oliver.mapper;

import com.oliver.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    @Select("select * from Users   ")
    public List<Users> findUsers();
    @Insert(value = "insert into Users(Username, Password, Phone, WeChatID) values (#{Username},#{Password},#{Phone},#{WeChatID})")
    void insertUsers(Users users);
}
