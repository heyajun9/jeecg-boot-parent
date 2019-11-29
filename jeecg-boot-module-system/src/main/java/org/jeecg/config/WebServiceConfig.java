package org.jeecg.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.jeecg.modules.demo.orderMain.webService.TestOrderMainWebService;
import org.jeecg.modules.demo.wmsItemTable.webService.WmsItemTableWebService;
import org.jeecg.modules.demo.wmsOrganization.webService.WmsOrganizationWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;


/**
 * @author Admin
 * @create 2019-09-23 9:18
 * @desc web服务配置
 **/
@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    WmsOrganizationWebService wmsOrganizationWebService;

    @Autowired
    WmsItemTableWebService wmsItemTableWebService;

    @Autowired
    TestOrderMainWebService testOrderMainWebService;

    /** JAX-WS **/
    @Bean
    public Endpoint organizationEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, wmsOrganizationWebService);
        endpoint.publish("/wmsOrganizationWebService");
        return endpoint;
    }
    @Bean
    public Endpoint itemTableEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, wmsItemTableWebService);
        endpoint.publish("/wmsItemTableWebService");
        return endpoint;
    }
    @Bean
    public Endpoint orderMainEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, testOrderMainWebService);
        endpoint.publish("/testOrderMainWebService");
        return endpoint;
    }

}
