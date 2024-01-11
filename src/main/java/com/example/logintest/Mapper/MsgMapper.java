package com.example.logintest.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.logintest.data_number.MsgPO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MsgMapper extends BaseMapper<MsgPO> {
    @Select("Select COUNT(*) from msg")
    long getcount();
    @Select("Select * from msg order by msgid desc limit 200")
    List<MsgPO> get200msg();
}
