package org.jeecg.xmlObjectTest;

import org.jeecg.common.util.webserviceUtil.XMLUtils;
import org.jeecg.modules.cas.util.XmlUtils;
import org.jeecg.modules.demo.wmsOrganization.entity.WmsOrganization;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;


@SuppressWarnings("unchecked")
public class Test {

    //从xml文件读取
    public static <T> T readFromFile(Class<T> clazz , String filePath) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new File(filePath));
    }

    //从xml字符串读取
    public static <T> T readFromString(Class<T> clazz , StringBuffer xmlStr) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())));
    }

    //把对象转化成xml输出到控制台
    public static <T> void writeToConsole(Class<T> clazz , T t) throws JAXBException{
        JAXBContext cxt = JAXBContext.newInstance(clazz);
        Marshaller marshaller = cxt.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 默认false表示xml指令存在
        marshaller.marshal(t, System.out);
    }

    //把对象转化成xml输出到文件
    public static <T> void writeToFile(Class<T> clazz , T t , String filePath) throws JAXBException{
        JAXBContext cxt = JAXBContext.newInstance(clazz);
        Marshaller marshaller = cxt.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 默认false表示xml指令存在
        marshaller.marshal(t, new File(filePath));
    }

    //把对象转化成xml字符串
    public static <T> String writeToString(Class<T> clazz , T t) throws JAXBException{
        JAXBContext cxt = JAXBContext.newInstance(clazz);
        Marshaller marshaller = cxt.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 默认false表示xml指令存在

        StringWriter writer = new StringWriter();
        marshaller.marshal(t, writer);
        return writer.toString();
    }

    public static void main(String[] args) throws JAXBException {
        //从xml文件读取
//        String filePath = "./src/vdi.xml";
//        Vdi vdis1 = Test.readFromFile(Vdi.class, filePath);
//        System.out.println(vdis1.toString());
//        for(Vdi vdi : vdis1){
//            System.out.println(vdi);
//        }

        //从xml字符串读取
        StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<wmsOrganization>"
                + "<id>1</id>"
                + "<organizationCode>6524552158933218</organizationCode>"
                + "</wmsOrganization>");
//        StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//                "<wmsOrganization>" +
//                "<organizationCode>fda4656</organizationCode>" +
//                "<organizationName>testNumber</organizationName>" +
//                "</wmsOrganization>");
//        //从xml字符串读取
//        StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//                + "<vdis size=\"2\" batch_number=\"20141212123456\" errmsg=\"\">"
//                + "<vdi>"
//                + "<id>1</id>"
//                + "<uuid>6524552158933218</uuid>"
//                + "<name>disk1</name>"
//                + "<disk_size>20</disk_size>"
//                + "</vdi>"
//                + "<vdi>"
//                + "<id>2</id>"
//                + "<uuid>6524552158933123</uuid>"
//                + "<name>disk2</name>"
//                + "<disk_size>40</disk_size>"
//                + "</vdi>"
//                + "</vdis>");
        WmsOrganization vdis2 = XMLUtils.readFromString(WmsOrganization.class, xmlStr);
        System.out.println(vdis2.toString());
//        for(Vdi vdi : vdis2){
//            System.out.println(vdi);
//        }

        //把对象转化成xml输出到控制台

//        Test.writeToConsole(Vdi.class, vdis2);

        //把对象转化成xml输出到文件
//        String filePath2 = "./src/test.xml";
//        Test.writeToFile(Vdi.class, vdis2, filePath2);

        //把对象转化成xml字符串
//        String xmlStr2 = Test.writeToString(Vdi.class, vdis2);
//        System.out.println(xmlStr2);
    }
}
