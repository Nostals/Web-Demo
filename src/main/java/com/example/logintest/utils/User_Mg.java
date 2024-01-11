package com.example.logintest.utils;

import com.example.logintest.Mapper.UserMapper;
import com.example.logintest.data_number.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Component
public class User_Mg {
    @Autowired
    UserMapper userMapper;
    public boolean AddUser(String UserId,String PassWord){
        UserPO p1=userMapper.selectById(UserId);
        if (p1!=null)
            return false;

        UserPO user = new UserPO();
        user.setName(UserId);
        user.setId(UserId);
        user.setPassword(PassWord);
        int insert = userMapper.insert(user);//如果没有设置id，那么会自动生成id
        System.out.println(insert);//受影响行数
        System.out.println(user);//id会自动回填
        return true;
    }
    public boolean EnsureUser(String UserId,String PassWord){
        UserPO p1=userMapper.selectById(UserId);
        if (p1==null)
            return false;
        return Objects.equals(p1.getPassword(), PassWord);
    }
    public String GetNameFromId(String UserId){
        UserPO p1=userMapper.selectById(UserId);
        return p1.getName();
    }
}
