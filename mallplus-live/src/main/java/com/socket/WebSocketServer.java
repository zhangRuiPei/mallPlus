package com.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/live/socket/{openId}/{courseId}")
@Component
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> videoWebSocketMap = new ConcurrentHashMap<>();
    private static List<String> users = new ArrayList<>();
    private static List userList = Collections.synchronizedList(users);
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";
    /**
     * 接收课程id
     */
    private String courseId = "";


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("openId") String openId, @PathParam("courseId") String courseId) {
//      if(StringUtils.isEmpty(socketType)){
//       return ;
//      }
        ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
        List<String> users = new ArrayList<>();
        List userList = Collections.synchronizedList(users);
        if (videoWebSocketMap.containsKey("webSocketMap" + courseId)) {
            webSocketMap = (ConcurrentHashMap<String, WebSocketServer>) videoWebSocketMap.get("webSocketMap" + courseId);
        }
        if (videoWebSocketMap.containsKey("userList" + courseId)) {
            userList = (List) videoWebSocketMap.get("userList" + courseId);
        }
        addOnlineCount();
        this.session = session;
        this.userId = openId;
        this.courseId = courseId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            //加入set中
        } else {
            webSocketMap.put(userId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        userList.add(userId);
        videoWebSocketMap.put("webSocketMap" + courseId, webSocketMap);
        videoWebSocketMap.put("userList" + courseId, userList);

        try {
            JSONObject jsonObject = new JSONObject();
            String msg = JSON.toJSONString(openId + "连接成功");
            //追加发送人(防止串改)
            jsonObject.put("fromUserId", this.userId);
            jsonObject.put("msg", msg);
            jsonObject.put("messageType", "system");
            jsonObject.put("userList", JSON.toJSONString(userList));

            sendMessage(webSocketMap, jsonObject.toJSONString());
        } catch (IOException e) {
            System.out.println(("用户:" + userId + ",网络异常!!!!!!"));
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
        List<String> users = new ArrayList<>();
        List userList = Collections.synchronizedList(users);
        if (videoWebSocketMap.containsKey("webSocketMap" + courseId)) {
            webSocketMap = (ConcurrentHashMap<String, WebSocketServer>) videoWebSocketMap.get("webSocketMap" + courseId);
        }else{
            return ;
        }
        if (videoWebSocketMap.containsKey("userList" + courseId)) {
            userList = (List) videoWebSocketMap.get("userList" + courseId);
        }
        userList.remove(userId);
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
            JSONObject jsonObject = new JSONObject();
            String msg = JSON.toJSONString(this.userId + "离开");
            //追加发送人(防止串改)
            jsonObject.put("fromUserId", this.userId);
            jsonObject.put("msg", msg);
            jsonObject.put("messageType", "system");
            jsonObject.put("userList", JSON.toJSONString(userList));
            sendMessage(webSocketMap, jsonObject.toJSONString());
        }
        videoWebSocketMap.put("webSocketMap" + courseId, webSocketMap);
        videoWebSocketMap.put("userList" + courseId, userList);

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
        List<String> users = new ArrayList<>();
        if (videoWebSocketMap.containsKey("webSocketMap" + courseId)) {
            webSocketMap = (ConcurrentHashMap<String, WebSocketServer>) videoWebSocketMap.get("webSocketMap" + courseId);
        }else{
            return ;
        }
        if (!StringUtils.isEmpty(message)) {

            if (message.equals("socketLiveEnd")) {
                try {
                String id = message.split(":")[1];
                    JSONObject jsonObject = new JSONObject();
            String msg = JSON.toJSONString(message);
                    jsonObject.put("fromUserId", this.userId);
                    jsonObject.put("messageType", "system");
                    jsonObject.put("msg", "socketLiveEnd");
                    sendMessage(webSocketMap, jsonObject.toJSONString());
                    videoWebSocketMap.remove("webSocketMap" + courseId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                //可以群发消息
                try {
                    //解析发送的报文
                    JSONObject jsonObject = new JSONObject();
                    String msg = JSON.toJSONString(message);
                    //追加发送人(防止串改)
                    jsonObject.put("fromUserId", this.userId);
                    jsonObject.put("messageType", "message");
                    jsonObject.put("msg", msg);
                    sendMessage(webSocketMap, jsonObject.toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
        List<String> users = new ArrayList<>();
        if (videoWebSocketMap.containsKey("webSocketMap" + courseId)) {
            webSocketMap = (ConcurrentHashMap<String, WebSocketServer>) videoWebSocketMap.get("webSocketMap" + courseId);
        }

        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
            sendMessage(webSocketMap, this.userId + "离开");
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(ConcurrentHashMap<String, WebSocketServer> webSocketMap, String message) throws IOException {
        for (Map.Entry<String, WebSocketServer> entry : webSocketMap.entrySet()) {
            entry.getValue().session.getBasicRemote().sendText(message);
        }
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        System.out.println(("发送消息到:" + userId + "，报文:" + message));
        if (!StringUtils.isEmpty(userId) && webSocketMap.containsKey(userId)) {
//            webSocketMap.get(userId).sendMessage(message);
        } else {
            System.out.println(("用户" + userId + ",不在线！"));
        }
    }

    public static synchronized int getOnlineCount() {
        if (onlineCount.get() == 100) {
            onlineCount.set(0);
            return 100;
        }
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount.getAndDecrement();
    }
}
