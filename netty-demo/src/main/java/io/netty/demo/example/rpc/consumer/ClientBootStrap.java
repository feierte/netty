package io.netty.demo.example.rpc.consumer;

import io.netty.demo.example.rpc.api.IUserService;
import io.netty.demo.example.rpc.consumer.proxy.RpcClientProxy;
import io.netty.demo.example.rpc.pojo.User;

/**
 * 测试类
 */
public class ClientBootStrap {
    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
        User user = userService.getById(1);
        System.out.println(user);
    }
}
