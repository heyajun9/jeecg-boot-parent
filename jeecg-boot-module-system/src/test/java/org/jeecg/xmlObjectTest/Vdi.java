package org.jeecg.xmlObjectTest;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Admin
 * @create 2019-11-26 9:01
 * @desc
 **/
@XmlRootElement(name="vdi")
@XmlAccessorType(XmlAccessType.FIELD)
public class Vdi {
    @XmlElement(name="id")
    private int id;
    @XmlElement(name="uuid")
    private String uuid;
    @XmlElement(name="name")
    private String name;
    @XmlElement(name="diskSize")
    private int diskSize;

    public int getId() {
        System.out.println("ccc");
        return id;
    }

    public void setId(int id) {
        System.out.println("ddd");
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

    @Override
    public String toString() {
        return "id:"+id
                + ",uuid:"+uuid
                + ",name:"+name
                + ",diskSize:"+diskSize;
    }
}
