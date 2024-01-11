package com.example.logintest.Tester;


import com.example.logintest.Mapper.UserMapper;
import com.example.logintest.data_number.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class logintestApplicariontest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void GetAllUserTest(){
        for (UserPO userPO : userMapper.selectList(null)) {
            System.out.println(userPO.toString());
        }
        UserPO p1=userMapper.selectById("001");
//        System.out.println(p1);
        System.out.println(Objects.equals(p1.getPassword(), "asjbdbkjasnxoua"));
    }
}
