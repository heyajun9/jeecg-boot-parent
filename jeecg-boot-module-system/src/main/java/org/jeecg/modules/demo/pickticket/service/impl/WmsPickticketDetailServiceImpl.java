package org.jeecg.modules.demo.pickticket.service.impl;

import org.jeecg.modules.demo.pickticket.entity.WmsPickticketDetail;
import org.jeecg.modules.demo.pickticket.mapper.WmsPickticketDetailMapper;
import org.jeecg.modules.demo.pickticket.service.IWmsPickticketDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 出库单明细
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
@Service
public class WmsPickticketDetailServiceImpl extends ServiceImpl<WmsPickticketDetailMapper, WmsPickticketDetail> implements IWmsPickticketDetailService {
	
	@Autowired
	private WmsPickticketDetailMapper wmsPickticketDetailMapper;
	
	@Override
	public List<WmsPickticketDetail> selectByMainId(String mainId) {
		return wmsPickticketDetailMapper.selectByMainId(mainId);
	}
}
