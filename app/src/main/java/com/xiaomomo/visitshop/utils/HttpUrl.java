package com.xiaomomo.visitshop.utils;

/**
 * Created by lihuanxing on 2017/7/15.
 * 一些网络请求的接口
 */

public class HttpUrl {
    public static final String BASE_URL = "http://192.168.0.100:8080";

    public static final String Login = BASE_URL + "/visitshop/login";//登录get
    public static final String FeedBack = BASE_URL + "/visitshop/feedback";//意见反馈post
    public static final String Announcement = BASE_URL + "/visitshop/announcement";//公告获取get
    public static final String Task = BASE_URL + "/visitshop/task";//任务获取get
    public static final String Info = BASE_URL + "/visitshop/info";//咨询获取get
    public static final String AppUpdate = BASE_URL + "/visitshop/appinfo";//app更新get
    public static final String HistroyShop = BASE_URL + "/visitshop/history";//历史巡店get
    public static final String ShopSelect = BASE_URL + "/visitshop/shop";//店面选择get
    public static final String VisitShopSubmit = BASE_URL + "/visitshop/visitupload";//巡店数据提交post
    public static final String Train = BASE_URL + "/visitshop/historytrain";//培训列表接口get
    public static final String TrainDetail = BASE_URL + "/visitshop/triandetail";//培训详情get
    public static final String TrainSubmit = BASE_URL + "/visitshop/trainupload";//培训数据提交post
    public static final String InterviewSubmit = BASE_URL + "/visitshop/interviewsubmit";//拜访提交post
    public static final String HistoryInterview = BASE_URL + "/visitshop/historyinterview";//历史拜访post
    public static final String UpdateUser = BASE_URL + "/visitshop/updateuser";//更新用户资料
    public static final String UpdateHead = BASE_URL + "/visitshop/uploadhead";//更新用户资料
}
