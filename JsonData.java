package edu.XUFE.online_class.utils;

public class JsonData {

    //状态码 0成功 1处理中 -1失败
    private Integer code;

    //业务数据
    private Object date;

    //信息描述
    private String msg;

    public JsonData(){}

    public JsonData(Integer code,Object date,String msg){
        this.code = code;
        this.date = date;
        this.msg = msg;
    }

    //成功不用返回数据
    public static JsonData buildSuccess(){
        return new JsonData(0,null,null);
    }
    //成功用返回数据
    public static JsonData buildSuccess(Object date){
        return new JsonData(0,date,null);
    }

    //失败
    public static JsonData buildError(String msg){
        return new JsonData(-1,null,msg);
    }
    //失败(自定义状态码)
    public static JsonData buildError(Integer code,String msg){
        return new JsonData(code,null,msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
