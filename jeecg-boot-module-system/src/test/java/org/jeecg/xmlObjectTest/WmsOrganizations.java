package org.jeecg.xmlObjectTest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Admin
 * @create 2019-11-26 8:15
 * @desc
 **/
@XmlRootElement(name = "wmsOrganizations")
@XmlAccessorType(XmlAccessType.FIELD)
public class WmsOrganizations {

    @XmlElement(name="id")
    private String id;
    /**创建人*/
    @XmlElement(name="createBy")
    private String createBy;
    /**创建日期*/
    @XmlElement(name="createTime")
    private java.util.Date createTime;
    /**更新人*/
    @XmlElement(name="updateBy")
    private String updateBy;
    /**更新日期*/
    @XmlElement(name="updateTime")
    private java.util.Date updateTime;
    /**所属部门*/
    @XmlElement(name="sysOrgCode")
    private String sysOrgCode;
    /**组织编号*/
    @XmlElement(name="organizationCode")
    private String organizationCode;
    /**组织名称*/
    @XmlElement(name="organizationName")
    private String organizationName;
    /**组织地址*/
    @XmlElement(name="organizationAddress")
    private String organizationAddress;
    /**省*/
    @XmlElement(name="province")
    private String province;
    /**市*/
    @XmlElement(name="city")
    private String city;
    /**区*/
    @XmlElement(name="area")
    private String area;

    @Override
    public String toString() {
        return "WmsOrganizations{" +
                "id='" + id + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", sysOrgCode='" + sysOrgCode + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", organizationAddress='" + organizationAddress + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
