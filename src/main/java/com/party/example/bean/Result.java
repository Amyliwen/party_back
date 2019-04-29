package com.party.example.bean;

/**
 * @author liwen
 * 用于封装返回的结果信息，配合@RestController注解实现将封装的信息以JSON格式return给前端
 */
public class Result {
    //判断结果
    private boolean success;
    //返回信息
    private String message;

    public Result(boolean success,String message) {
        this.success = success;
        this.message = message;
    }
    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
