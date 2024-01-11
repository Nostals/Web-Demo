package com.example.logintest.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.logintest.Mapper.MsgMapper;

import com.example.logintest.data_number.MsgPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class Msg_Mg {
    @Autowired
    private MsgMapper msgMapper;
    long msg_counter;
    List<MsgPO> msgPOList;
    int keep_counter;
    @PostConstruct
    public void init(){
        msg_counter=msgMapper.getcount();
        msgPOList=msgMapper.get200msg();
        System.out.println(msgPOList);
        keep_counter=msgPOList.size();
    }
    public boolean AddMsg(String msg,String time,String userid){
        try{
            msg_counter++;
            MsgPO m1 = new MsgPO();
            m1.setMsgid(Long.toString(msg_counter));
            m1.setMsg(msg);
            m1.setTime(time);
            m1.setUserid(userid);
            msgMapper.insert(m1);
            msgPOList.add(m1);
            return true;
        }
        catch (Exception e){
            msg_counter--;
            return false;
        }

    }
    public List<MsgPO> Checkmsg(int tag,int num){
        if (tag+num<=keep_counter){
            return msgPOList.subList(tag,tag+num);
        }
        else {
            QueryWrapper<MsgPO> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("msgid");
            wrapper.last("limit"+" "+tag+","+num);
            return msgMapper.selectList(wrapper);
        }
//        return null;
    }
}
