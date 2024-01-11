package com.example.logintest.contrler;

import com.alibaba.fastjson.JSONObject;
import com.example.logintest.utils.User_Mg;
import com.example.logintest.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.logintest.utils.Token_Mg;
import com.example.logintest.utils.RSAUtils;

import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class user_Contrler {
    user_Contrler(){
        RSAUtils.createRSAKeys();
    }
    @Autowired
    User_Mg myUser_mg;
    @ResponseBody   //标注返回值为Body
    @GetMapping("/admin/login")
    public Object login(String userid,String password){
        String md5_password=RSAUtils.decode(password,RSAUtils.GetprivateKey());
        String md5_password_st=md5_password+userid.charAt(0);
        String md5_password_st_twic=DigestUtils.md5Hex(md5_password_st);

        JSONObject jsonObject = new JSONObject();
        if (myUser_mg.EnsureUser(userid,md5_password_st_twic)){
            String token = Token_Mg.sign(userid);
            jsonObject.put("token",token);
//            jsonObject.put("user",u);
            jsonObject.put("msg","登录成功");
            jsonObject.put("code",200);
        }else{
            jsonObject.put("msg","账号或密码错误");
            jsonObject.put("code",500);
        }
        return jsonObject;
    }
    @ResponseBody   //标注返回值为Body
    @GetMapping("/admin/register")
    public Object register(String userid,String password){
        String md5_password=RSAUtils.decode(password,RSAUtils.GetprivateKey());
        String md5_password_st=md5_password+userid.charAt(0);
        String md5_password_st_twic=DigestUtils.md5Hex(md5_password_st);

        JSONObject jsonObject = new JSONObject();
        if (myUser_mg.AddUser(userid,md5_password_st_twic)){
            jsonObject.put("msg","注册成功");
            jsonObject.put("code",200);
        }else{
            jsonObject.put("msg","id已被占用");
            jsonObject.put("code",500);
        }
        return jsonObject;
    }
    @ResponseBody   //标注返回值为Body
    @GetMapping("/admin/getpublickey")
    public String getpublickey(){
        return RSAUtils.GetpublicKey();
    }
    @ResponseBody   //标注返回值为Body
    @GetMapping("/tokentest")
    public String suretoken(HttpServletRequest request){

//        return "success";
        String token = request.getHeader("token"); //前端vue将token添加在请求头中
        if(token != null){
            return Token_Mg.verify(token);
        }
        return null;
    }
    @PostMapping("/websocket/getall_oline")
    public List<String> getolineuser(){
        List<String> userlist= new ArrayList<>();
        for (String s : WebSocketServer.sessionMap.keySet()) {
            userlist.add(myUser_mg.GetNameFromId(s));
        }
        return userlist;
    }
}
