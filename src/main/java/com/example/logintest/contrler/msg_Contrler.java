package com.example.logintest.contrler;

import com.example.logintest.utils.Msg_Mg;
import com.example.logintest.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class msg_Contrler {
    @ResponseBody   //标注返回值为Body
    @GetMapping("/msg/getmsg")
    public Object getmsg(int tag,int num){
        return WebSocketServer.getmsg(tag,num);
    }
//    @GetMapping("/msg/addmsg")
//    public Object addmsg(String msg,String time,String userid){
//        return msg_mg.AddMsg(msg,time,userid);
//    }
}
