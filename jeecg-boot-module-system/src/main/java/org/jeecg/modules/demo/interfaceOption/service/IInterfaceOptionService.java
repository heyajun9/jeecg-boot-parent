package org.jeecg.modules.demo.interfaceOption.service;

import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import org.jeecg.modules.demo.interfaceOption.entity.InterfaceOption;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 接口配置
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
public interface IInterfaceOptionService extends IService<InterfaceOption> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(InterfaceOption interfaceOption,List<TableOption> tableOptionList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(InterfaceOption interfaceOption,List<TableOption> tableOptionList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
