package org.jeecg.webServiceTest;

import org.jeecg.common.util.webserviceUtil.HttpSoapClientUtil;

import static org.jeecg.common.util.webserviceUtil.HttpSoapClientUtil.doPostSoap1_1;

/**
 * @author Admin
 * @create 2019-11-28 15:42
 * @desc
 **/
public class XmlHttpClientTest {
    public static void main(String[] args) {
        String orderSoapXml = "<?xml version = \"1.0\" ?>"
                + "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservices.b.com\">"
                + "   <soapenv:Header/>"
                + "   <soapenv:Body>"
                + "      <web:order soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
                + "         <in0 xsi:type=\"web:OrderRequest\">"
                + "            <mobile xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">?</mobile>"
                + "            <orderStatus xsi:type=\"xsd:int\">?</orderStatus>"
                + "            <productCode xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">?</productCode>"
                + "         </in0>" + "      </web:order>"
                + "   </soapenv:Body>" + "</soapenv:Envelope>";
        String querySoapXml = "<?xml version = \"1.0\" ?>"
                + "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservices.b.com\">"
                + "   <soapenv:Header/>"
                + "   <soapenv:Body>"
                + "      <web:query soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
                + "         <in0 xsi:type=\"web:QueryRequest\">"
                + "            <endTime xsi:type=\"xsd:dateTime\">?</endTime>"
                + "            <mobile xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">?</mobile>"
                + "            <startTime xsi:type=\"xsd:dateTime\">?</startTime>"
                + "         </in0>" + "      </web:query>"
                + "   </soapenv:Body>" + "</soapenv:Envelope>";
        String param =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webService.wmsOrganization.demo.modules.jeecg.org\">" +
                "   <soapenv:Header/>" +
                "   <soapenv:Body>" +
                "      <web:add>" +
                "         <!--Optional:-->" +
                "         <organization>" +
                "            <id>43265645</id>" +
                "            <!--Optional:-->" +
                "            <organizationAddress>65</organizationAddress>" +
                "            <!--Optional:-->" +
                "            <organizationCode>62456</organizationCode>" +
                "         </organization>" +
                "      </web:add>" +
                "   </soapenv:Body>" +
                "</soapenv:Envelope>";
        String postUrl = "http://localhost:8080/services/WebServiceFromB";
        String organizationUrl="http://localhost:8080/jeecg-boot/services/wmsOrganizationWebService";
        //采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务
//       doPostSoap1_1(postUrl, orderSoapXml, "");
//        doPostSoap1_1(postUrl, querySoapXml, "");
        doPostSoap1_1(organizationUrl,param,"");

        //采用SOAP1.2调用服务端，这种方式只能调用服务端为soap1.2的服务
        //doPostSoap1_2(postUrl, orderSoapXml, "order");
        //doPostSoap1_2(postUrl, querySoapXml, "query");
    }
}
