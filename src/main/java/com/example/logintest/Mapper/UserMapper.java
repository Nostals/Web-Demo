package com.example.logintest.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.logintest.data_number.UserPO;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends BaseMapper<UserPO> {
}
