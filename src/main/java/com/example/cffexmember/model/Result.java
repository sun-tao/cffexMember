package com.example.cffexmember.model;
import lombok.Data;

@Data
public class Result<T> {

    /**
     * 消息
     */
    private String message;

    /**
     * 成功状态
     */
    private int code;

    /**
     * 数据
     */
    private T data;

    /**
     * 创建并返回成功结果，无数据
     *
     * @param message 消息
     * @return 成功结果
     */
    public static <T> Result<T> resultSuccess(String message) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 创建并返回成功结果，包含数据
     *
     * @param message 消息
     * @param data    数据体
     * @return 成功结果
     */
    public static <T> Result<T> resultSuccess(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 创建并返回失败结果
     *
     * @param message 消息
     * @return 失败结果
     */
    public static <T> Result<T> resultFailed(String message) {
        Result<T> result = new Result<>();
        result.setCode(-1);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

}
