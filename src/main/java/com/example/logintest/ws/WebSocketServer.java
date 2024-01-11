package com.example.logintest.ws;



import com.example.logintest.utils.Msg_Mg;
import com.example.logintest.utils.Token_Mg;
import com.example.logintest.utils.User_Mg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RestController;

@ServerEndpoint(value = "/chat/{userId}")    // 这里设置的是访问后端接口
@Component
public class WebSocketServer {

    // 这部分是日志打印
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    // 在线连接数
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    public static Msg_Mg myMsg_mg;
    private static User_Mg myUser_mg;
    @Autowired
    public void setMyUser_mg(User_Mg User_mg,Msg_Mg Msg_mg){
        myUser_mg=User_mg;
        myMsg_mg=Msg_mg;
    }
    public static Object getmsg(int tag,int num){
        return myMsg_mg.Checkmsg(tag, num);
    }
    public static String Getnowtime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    // 当有用户建立连接到服务器的时候触发
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId){
        // 建立连接的时候就将该用户的session存起来，方便后续的信息返回
        sessionMap.put(userId, session);
        log.info("新聊天用户={}，当前在线人数：{}",userId, sessionMap.size());
    }

    // 关闭时触发
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId){
        sessionMap.remove(userId);
        log.info("有一连接关闭，移除聊天用户={}，当前在线人数为：{}", userId, sessionMap.size());
    }

    // 当用户发送消息时触发
    @OnMessage
    public void onMessage(String parmas, Session session, @PathParam("userId") String userId){
        System.out.println(parmas);
        log.info("服务器收到聊天用户={}的消息：{}", userId, parmas);
        JSONObject jsonObject = new JSONObject();
        String time=Getnowtime();
        jsonObject.put("username",myUser_mg.GetNameFromId(userId));
        jsonObject.put("text",parmas);
        jsonObject.put("time",time);
        sendAllMessage(jsonObject.toString());

        myMsg_mg.AddMsg(parmas,time,userId);
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.error("发生错误");
        error.printStackTrace();
    }

    // 服务器发送消息给客户端
    private void sendMessage(String message, Session toSession){
        try {
            log.info("服务器给用户【{}】发送消息：{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        }catch (Exception e){
            log.error("服务器发送消息给客户端失败");
        }
    }

    // 服务器发送信息给所有客户端（这步可拓展到群聊）
    private void sendAllMessage(String message){
        try {
            for(Session session : sessionMap.values()){
                log.info("服务器给全用户【{}】发送消息：{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        }catch (Exception e){
            log.error("服务器发送消息给客户端失败");
        }
    }


    // 这是mongodb存到数据库的操作
//    private void insertChatData(JSONObject chatData, String chatId) {
//        Document document = Document.parse(chatData.toString());
//
//        Document query = new Document("chatId", chatId);
//        Document update = new Document("$push", new Document("chatData", document));
//
//        UpdateResult updateResult = collection.updateOne(query, update, new UpdateOptions().upsert(true));
//        System.out.println(updateResult);
//    }
}