package com.hy.springboot.demo.common.bean;

/**
 * @user yang.he
 * @date 2019/10/21
 * @introduce
 **/
public class InvokeResult {

    private String isSuccess;
    private String returnMsg;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public InvokeResult(String isSuccess, String returnMsg) {
        this.isSuccess = isSuccess;
        this.returnMsg = returnMsg;
    }

}
