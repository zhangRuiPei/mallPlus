package com.zrp.mallplus.util.vo;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public class LocalResoultVo {

    private String status;

    private String msg;

    private LocaltionVo result;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocaltionVo getResult() {
        return result;
    }

    public void setResult(LocaltionVo result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "LocalResoultVo{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
