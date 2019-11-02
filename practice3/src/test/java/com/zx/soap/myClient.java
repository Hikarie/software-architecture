package com.zx.soap;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;

public class myClient {

    public String method = "sendEmail";
    public String url = "hikarinoda@163.com";
    public String payload = "hello";
    private Client client;
    private Endpoint endpoint;

    public void create(){
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        client = factory.createClient("http://localhost:8080/email/ws/soap?wsdl");
        endpoint = client.getEndpoint();
    }

    public String invoke() {
        QName name = new QName(endpoint.getService().getName().getNamespaceURI(), method);
        Object[] objects = null;
        try {
            if(method.equals("validateEmailAddress")){
                objects = client.invoke(name, url);
            }
            else if(method.equals("sendEmail")) {
                objects = client.invoke(name, url, payload);
            }
            System.out.println(objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String)objects[0];
    }

}
