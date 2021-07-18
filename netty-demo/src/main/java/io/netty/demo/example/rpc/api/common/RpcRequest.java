package io.netty.demo.example.rpc.api.common;

import lombok.Data;

/**
 * 封装的请求对象
 *
 * @author Jie Zhao
 * @date 2021/7/18 21:17
 */
@Data
public class RpcRequest {

    /**
     * 请求对象的ID
     */
    private String requestId;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;
}
