package com.hy.springboot.demo.common.bean;

/**
 * @user yang.he
 * @date 2020/1/8
 * @introduce    新的通用调用返回接口
 **/
public class NewInvokeResult {

    private String isSuccess;
    private String code;
    private String returnMsg;


    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public NewInvokeResult(String isSuccess, String returnMsg) {
        this.isSuccess = isSuccess;
        this.returnMsg = returnMsg;
    }

    public NewInvokeResult(String isSuccess, String code, String returnMsg) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.returnMsg = returnMsg;
    }
}
