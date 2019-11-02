package com.zx.rest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Path("/email")
public class RestServiceImpl implements RestService{

    String accessKeyId = "";    // filling this by your accessKeyId
    String secret = "";     // filling this by your secret

    @GET
    @Path("/test")
    public String test(@QueryParam("name")String name) {
        return "welcome,"+name+"!";
    }

    @GET
    @Path("/sendEmail")
    @Produces(MediaType.TEXT_PLAIN)
    public String sendEmail(@QueryParam("url")String _url, @QueryParam("payload")String _payload) {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId , secret);
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        request.setProtocol(com.aliyuncs.http.ProtocolType.HTTPS);
        try {
            request.setAccountName("hikari@service.hikari.monster");
            request.setFromAlias("hikari");
            request.setAddressType(1);
            request.setReplyToAddress(true);
            request.setToAddress(_url);
            request.setSubject("邮件主题");
            request.setHtmlBody(_payload);
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        }  catch (ServerException e) {
            System.out.println("发送失败");
            e.printStackTrace();
            return "Sever error";
        }catch (ClientException e) {
            System.out.println("发送失败");
            e.printStackTrace();
            return "Client error";
        }
        System.out.println("发送成功");
        return "send succeed";
    }

    //批量发送邮件
    @GET
    @Path("/sendEmail")
    @Produces(MediaType.TEXT_PLAIN)
    public String sendEmailBatch(@QueryParam("url") List<String> _url, @QueryParam("payload")String _payload) {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId , secret);
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        request.setProtocol(com.aliyuncs.http.ProtocolType.HTTPS);
        try {
            request.setAccountName("hikari@service.hikari.monster");
            request.setFromAlias("hikari");
            request.setAddressType(1);
            request.setReplyToAddress(true);
            for(String url:_url) {
                request.setToAddress(url);
                request.setSubject("Service based on REST");
                request.setHtmlBody(_payload);
                SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            }
        } catch (ServerException e) {
            System.out.println("发送失败");
            e.printStackTrace();
            return "Sever error";
        }catch (ClientException e) {
            System.out.println("发送失败");
            e.printStackTrace();
            return "Client error";
        }
        System.out.println("发送成功");
        return "send succeed";
    }

    @GET
    @Path("/validateEmailAddress")
    @Produces(MediaType.TEXT_PLAIN)
    //验证是否为有效的邮件地址
    public String validateEmailAddress(@QueryParam("url")String _url) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(_url);
        boolean result = matcher.matches();
        if(result==true)
            return "the url is valid";
        else return "the url is illegal";
    }
}
