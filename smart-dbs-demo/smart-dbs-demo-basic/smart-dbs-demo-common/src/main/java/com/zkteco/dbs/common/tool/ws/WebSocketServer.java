/*
 * File Name: WebSocket
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.ws;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * WebSocket
 *
 * @author sheldon.wu
 * @date 2020/12/2 15:52
 * @since 1.0.0
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    private static Log log = LogFactory.get(WebSocketServer.class);

    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的sid
     */
    private String sid;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

    /**
     * OnOpen
     * 连接建立成功调用的方法
     * @param session
     * @param sid
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/3 11:59
     * @since 1.0.0
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "sid") String sid) {
        this.session = session;
        this.sid = sid;
        // 设置长连接超时时间
        session.setMaxIdleTimeout(120000);
        //判断是否存在
        if (webSocketSet.containsKey(sid)) {
            webSocketSet.remove(sid);
        }
        // sid是用来表示唯一客户端，如果需要指定发送，需要指定发送通过sid来区分
        webSocketSet.put(sid, this);
        log.info("用户连接:" + sid);
    }


    /**
     * OnClose
     * 连接关闭调用的方法
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/3 12:00
     * @since 1.0.0
     */
    @OnClose
    public void OnClose() {
        if (webSocketSet.containsKey(sid)) {
            webSocketSet.remove(sid);
        }
        log.info("用户退出:" + sid);
    }

    /**
     * OnMessage
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/3 13:46
     * @since 1.0.0
     */
    @OnMessage
    public void OnMessage(String message, Session session) {
        log.info("用户消息:" + sid + ",报文:" + message);
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromSid", this.sid);
                String toSid = jsonObject.getString("toSid");
                //传送给对应toSid用户的websockets
                if (StringUtils.isNotBlank(toSid) && webSocketSet.containsKey(toSid)) {
                    webSocketSet.get(toSid).sendMessage(jsonObject.toJSONString());
                } else {
                    log.error("请求的sid:" + toSid + "不在该服务器上");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * onError
     *
     * @param session
     * @param throwable
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/3 13:31
     * @since 1.0.0
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("用户错误:" + this.sid + ",原因:" + throwable.getMessage());
        throwable.printStackTrace();
    }

    /**
     * sendMessage
     * 实现服务器主动推送
     * @param message
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/3 11:55
     * @since 1.0.0
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * sendInfo
     * 发送自定义消息
     * @param message
     * @param sid
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/3 11:56
     * @since 1.0.0
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) {
        log.info("发送消息到:" + sid + "，报文:" + message);
        try {
            if (StringUtils.isNotBlank(sid) && webSocketSet.containsKey(sid)) {
                webSocketSet.get(sid).sendMessage(message);
            } else {
                log.error("用户" + sid + ",不在线！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
