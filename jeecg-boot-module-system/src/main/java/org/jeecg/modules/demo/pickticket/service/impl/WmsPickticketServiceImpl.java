package org.jeecg.modules.demo.pickticket.service.impl;

import org.jeecg.modules.demo.pickticket.entity.WmsPickticket;
import org.jeecg.modules.demo.pickticket.entity.WmsPickticketDetail;
import org.jeecg.modules.demo.pickticket.mapper.WmsPickticketDetailMapper;
import org.jeecg.modules.demo.pickticket.mapper.WmsPickticketMapper;
import org.jeecg.modules.demo.pickticket.service.IWmsPickticketService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 出库单
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
@Service
public class WmsPickticketServiceImpl extends ServiceImpl<WmsPickticketMapper, WmsPickticket> implements IWmsPickticketService {

	@Autowired
	private WmsPickticketMapper wmsPickticketMapper;
	@Autowired
	private WmsPickticketDetailMapper wmsPickticketDetailMapper;
	
	@Override
	@Transactional
	public void saveMain(WmsPickticket wmsPickticket, List<WmsPickticketDetail> wmsPickticketDetailList) {
		wmsPickticketMapper.insert(wmsPickticket);
		if(wmsPickticketDetailList!=null && wmsPickticketDetailList.size()>0) {
			for(WmsPickticketDetail entity:wmsPickticketDetailList) {
				//外键设置
				entity.setWmsPickticketId(wmsPickticket.getId());
				wmsPickticketDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WmsPickticket wmsPickticket,List<WmsPickticketDetail> wmsPickticketDetailList) {
		wmsPickticketMapper.updateById(wmsPickticket);
		
		//1.先删除子表数据
		wmsPickticketDetailMapper.deleteByMainId(wmsPickticket.getId());
		
		//2.子表数据重新插入
		if(wmsPickticketDetailList!=null && wmsPickticketDetailList.size()>0) {
			for(WmsPickticketDetail entity:wmsPickticketDetailList) {
				//外键设置
				entity.setWmsPickticketId(wmsPickticket.getId());
				wmsPickticketDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wmsPickticketDetailMapper.deleteByMainId(id);
		wmsPickticketMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wmsPickticketDetailMapper.deleteByMainId(id.toString());
			wmsPickticketMapper.deleteById(id);
		}
	}
	
}
