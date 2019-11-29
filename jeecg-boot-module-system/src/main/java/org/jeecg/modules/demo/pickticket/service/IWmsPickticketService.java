package org.jeecg.modules.demo.pickticket.service;

import org.jeecg.modules.demo.pickticket.entity.WmsPickticketDetail;
import org.jeecg.modules.demo.pickticket.entity.WmsPickticket;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 出库单
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
public interface IWmsPickticketService extends IService<WmsPickticket> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(WmsPickticket wmsPickticket,List<WmsPickticketDetail> wmsPickticketDetailList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WmsPickticket wmsPickticket,List<WmsPickticketDetail> wmsPickticketDetailList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
