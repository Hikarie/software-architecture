package com.zx.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/email")
public interface RestService {
    @GET
    @Path("/test")
    public String test(@QueryParam("name")String name);
    @GET
    @Path("/sendEmail")
    public String sendEmail(@QueryParam("url")String _url, @QueryParam("payload")String _payload); //邮件地址为_url，内容为_payload
    @GET
    @Path("/sendEmailBatch")
    public String sendEmailBatch(@QueryParam("url")List<String> _url, @QueryParam("payload")String _payload); //批量发送邮件
    @GET
    @Path("/validateEmailAddress")
    public String validateEmailAddress(@QueryParam("url")String _url); //验证是否为有效的邮件地址
}
