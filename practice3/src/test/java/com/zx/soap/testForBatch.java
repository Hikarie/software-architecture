package com.zx.soap;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.List;

public class testForBatch {
    public static void main(String[] args) {
        String method = "sendEmailBatch";
        List<String> urls = Arrays.asList("530724328@qq.com","hikarinoda@163.com","zxin@cug.edu.cn");
        String payload = "test for send email batch";
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient("http://localhost:8080/email/ws/soap?wsdl");
        Endpoint endpoint = client.getEndpoint();
        QName name = new QName(endpoint.getService().getName().getNamespaceURI(), method);
        Object[] objects = null;
        try {
            objects = client.invoke(name, urls, payload);
            System.out.println(objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
