package com.zx.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface SoapService {

    @WebMethod(operationName = "sendEmail")
    public String sendEmail(String _url,String _payload); //邮件地址为_url，内容为_payload

    @WebMethod(operationName = "sendEmailBatch")
    public String sendEmailBatch(List<String> _url, String _payload); //批量发送邮件

    @WebMethod(operationName = "validateEmailAddress")
    public String validateEmailAddress(String _url); //验证是否为有效的邮件地址

}
