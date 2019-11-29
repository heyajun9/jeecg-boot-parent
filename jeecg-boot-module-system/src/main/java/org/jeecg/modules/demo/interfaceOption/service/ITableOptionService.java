package org.jeecg.modules.demo.interfaceOption.service;

import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 字段对应表
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
public interface ITableOptionService extends IService<TableOption> {

	public List<TableOption> selectByMainId(String mainId);
}
