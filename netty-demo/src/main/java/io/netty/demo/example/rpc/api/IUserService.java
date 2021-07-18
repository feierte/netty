package io.netty.demo.example.rpc.api;

import io.netty.demo.example.rpc.pojo.User;

/**
 * 用户服务
 * @author Jie Zhao
 * @date 2021/7/18 21:15
 */
public interface IUserService {

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    User getById(int id);
}
