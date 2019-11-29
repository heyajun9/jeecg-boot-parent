package org.jeecg.common.util.webserviceUtil;

import org.apache.poi.ss.formula.functions.T;

import java.io.*;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Admin
 * @create 2019-11-25 13:36
 * @desc XML转换成对象工具类
 **/
public class XMLUtils {

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
}
