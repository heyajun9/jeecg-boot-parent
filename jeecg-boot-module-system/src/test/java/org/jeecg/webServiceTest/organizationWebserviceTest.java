package org.jeecg.webServiceTest;


import jdk.nashorn.internal.codegen.CompilerConstants;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.webserviceUtil.XMLUtils;
import org.jeecg.modules.demo.wmsOrganization.entity.WmsOrganization;
import org.jeecg.modules.demo.wmsOrganization.webService.WmsOrganizationWebService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysUserMapper;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.xmlObjectTest.WmsOrganizations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Admin
 * @create 2019-11-25 11:28
 * @desc 组织webservice客户端访问(暂时不用)
 **/
public class organizationWebserviceTest {
    private static String url = "http://localhost:8080/jeecg-boot/services/wmsOrganizationWebService?wsdl";
    private static String targetNamespace = "http://webService.wmsOrganization.demo.modules.jeecg.org";

    private static final String USER_NAME="admin";

    private static final String PASSWORD="123456";
//    @Autowired
//    private ISysUserService userService;

        /**
         * 1.代理类工厂的方式,需要拿到对方的接口地址
         */
        public static void proxyFactoryClient(){
            try {
                // 接口地址
                String address = url;
                // 代理工厂
                JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
                // 设置代理地址
                jaxWsProxyFactoryBean.setAddress(address);
                // 设置接口类型
                jaxWsProxyFactoryBean.setServiceClass(WmsOrganizationWebService.class);
                // 创建一个代理接口实现
                WmsOrganizationWebService us = (WmsOrganizationWebService) jaxWsProxyFactoryBean.create();
                // 数据准备
                String param =  "         <!--Optional:-->" +
                        "         <wmsOrganization>" +
                        "            <!--Optional:-->" +
                        "            <area>?</area>" +
                        "            <!--Optional:-->" +
                        "            <city>?</city>" +
                        "            <!--Optional:-->" +
                        "            <createBy>?</createBy>" +
                        "            <!--Optional:-->" +
                        "            <createTime>?</createTime>" +
                        "            <!--Optional:-->" +
                        "            <id>test43253645</id>" +
                        "            <!--Optional:-->" +
                        "            <organizationAddress>?</organizationAddress>" +
                        "            <!--Optional:-->" +
                        "            <organizationCode>?</organizationCode>" +
                        "            <!--Optional:-->" +
                        "            <organizationName>?</organizationName>" +
                        "            <!--Optional:-->" +
                        "            <province>?</province>" +
                        "            <!--Optional:-->" +
                        "            <sysOrgCode>?</sysOrgCode>" +
                        "            <!--Optional:-->" +
                        "            <updateBy>?</updateBy>" +
                        "            <!--Optional:-->" +
                        "            <updateTime>?</updateTime>" +
                        "         </wmsOrganization>" ;
                // 调用代理接口的方法调用并返回结果
                WmsOrganizations organization=  XMLUtils.readFromString(WmsOrganizations.class,new StringBuffer(param));
                System.out.println(organization.toString());
//                Result<WmsOrganization> result = us.add(organization);
//                System.out.println("返回结果:" + result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 2：动态调用
         */
        public static void moveProxyClient() {
            // 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(url);
            // 需要密码的情况需要加上用户名和密码
//            SysUser user = userService.getUserByName(USER_NAME);
//            String passwordEncode = PasswordUtil.encrypt(USER_NAME, PASSWORD, user.getSalt());
//            client.getOutInterceptors().add(passwordEncode);
            Object[] objects = new Object[0];
            try {
                String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<wmsOrganization><organizationCode>wufdsag3534</organizationCode><organizationName>fds45366</organizationName></wmsOrganization>";
                // invoke("方法名",参数1,参数2,参数3....);
                objects = client.invoke("add", param);
                System.out.println("返回数据:" + objects[0]);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        moveProxyClient();
    }

}
