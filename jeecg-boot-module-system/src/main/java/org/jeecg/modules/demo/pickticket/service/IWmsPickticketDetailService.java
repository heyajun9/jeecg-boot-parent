package org.jeecg.modules.demo.pickticket.service;

import org.jeecg.modules.demo.pickticket.entity.WmsPickticketDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 出库单明细
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
public interface IWmsPickticketDetailService extends IService<WmsPickticketDetail> {

	public List<WmsPickticketDetail> selectByMainId(String mainId);
}
