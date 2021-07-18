package io.netty.demo.example.rpc.api.common;

import lombok.Data;

/**
 * 封装的响应对象
 *
 * @author Jie Zhao
 * @date 2021/7/18 21:17
 */
@Data
public class RpcResponse {

    /**
     * 响应ID
     */
    private String requestId;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回的结果
     */
    private Object result;

}
