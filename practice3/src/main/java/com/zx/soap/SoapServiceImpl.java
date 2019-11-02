package com.zx.soap;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("SoapService")
@WebService
public class SoapServiceImpl implements SoapService{

    String accessKeyId = "";    // filling this by your accessKeyId
    String secret = "";     // filling this by your secret

    @WebMethod(operationName = "sendEmail")
    public String sendEmail(String _url,String _payload) {
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
            request.setSubject("Service based on SOAP");    //邮件名
            request.setHtmlBody(_payload);  //邮件内容
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        }  catch (ServerException e) {
            System.out.println("failed");
            e.printStackTrace();
            return "Sever error";
        }catch (ClientException e) {
            System.out.println("failed");
            e.printStackTrace();
            return "Client error";
        }
        System.out.println("succeed");
        return "send succeed";
    }

    @WebMethod(operationName = "sendEmailBatch")
    public String sendEmailBatch(List<String> _url, String _payload) {
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
                request.setSubject("Service based on SOAP");
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

    @WebMethod(operationName = "validateEmailAddress")
    public String validateEmailAddress(String _url) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(_url);
        boolean result = matcher.matches();
        if(result==true)
            return "the url is valid";
        else return "the url is illegal";
    }
}
