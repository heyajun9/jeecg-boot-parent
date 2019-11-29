package org.jeecg.modules.demo.pickticket.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.aspect.annotation.InterfaceLog;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.pickticket.entity.WmsPickticketDetail;
import org.jeecg.modules.demo.pickticket.entity.WmsPickticket;
import org.jeecg.modules.demo.pickticket.vo.WmsPickticketPage;
import org.jeecg.modules.demo.pickticket.service.IWmsPickticketService;
import org.jeecg.modules.demo.pickticket.service.IWmsPickticketDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 出库单
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
@RestController
@RequestMapping("/pickticket/wmsPickticket")
@Slf4j
public class WmsPickticketController {
	@Autowired
	private IWmsPickticketService wmsPickticketService;
	@Autowired
	private IWmsPickticketDetailService wmsPickticketDetailService;
	
	/**
	  * 分页列表查询
	 * @param wmsPickticket
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WmsPickticket>> queryPageList(WmsPickticket wmsPickticket,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<WmsPickticket>> result = new Result<IPage<WmsPickticket>>();
		QueryWrapper<WmsPickticket> queryWrapper = QueryGenerator.initQueryWrapper(wmsPickticket, req.getParameterMap());
		Page<WmsPickticket> page = new Page<WmsPickticket>(pageNo, pageSize);
		IPage<WmsPickticket> pageList = wmsPickticketService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param wmsPickticketPage
	 * @return
	 */
	@InterfaceLog(value = "出库单-添加")
	@PostMapping(value = "/add")
	public Result<WmsPickticket> add(@RequestBody WmsPickticketPage wmsPickticketPage) {
		Result<WmsPickticket> result = new Result<WmsPickticket>();
		try {
			WmsPickticket wmsPickticket = new WmsPickticket();
			BeanUtils.copyProperties(wmsPickticketPage, wmsPickticket);
			
			wmsPickticketService.saveMain(wmsPickticket, wmsPickticketPage.getWmsPickticketDetailList());
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	 /**
	  *   批量添加
	  * @param wmsPickticketPages
	  * @return
	  */
	 @InterfaceLog(value = "出库单-添加")
	 @PostMapping(value = "/addList")
	 public Result<WmsPickticket> addList(@RequestBody List<WmsPickticketPage> wmsPickticketPages) {
		 Result<WmsPickticket> result = new Result<WmsPickticket>();
		 try {
			 for (WmsPickticketPage wmsPickticketPage : wmsPickticketPages) {


				 WmsPickticket wmsPickticket = new WmsPickticket();
				 BeanUtils.copyProperties(wmsPickticketPage, wmsPickticket);

				 wmsPickticketService.saveMain(wmsPickticket, wmsPickticketPage.getWmsPickticketDetailList());
				 result.success("添加成功！");
			 }
			 } catch(Exception e){
				 log.error(e.getMessage(), e);
				 result.error500("操作失败");
			 }
		 return result;
	 }
	
	/**
	  *  编辑
	 * @param wmsPickticketPage
	 * @return
	 */
	@InterfaceLog(value = "出库单-编辑")
	@PutMapping(value = "/edit")
	public Result<WmsPickticket> edit(@RequestBody WmsPickticketPage wmsPickticketPage) {
		Result<WmsPickticket> result = new Result<WmsPickticket>();
		WmsPickticket wmsPickticket = new WmsPickticket();
		BeanUtils.copyProperties(wmsPickticketPage, wmsPickticket);
		WmsPickticket wmsPickticketEntity = wmsPickticketService.getById(wmsPickticket.getId());
		if(wmsPickticketEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = wmsPickticketService.updateById(wmsPickticket);
			wmsPickticketService.updateMain(wmsPickticket, wmsPickticketPage.getWmsPickticketDetailList());
			result.success("修改成功!");
		}
		
		return result;
	}

	 /**
	  *  批量编辑
	  * @param wmsPickticketPages
	  * @return
	  */
	 @InterfaceLog(value = "出库单-编辑")
	 @PutMapping(value = "/editList")
	 public Result<WmsPickticket> editList(@RequestBody List<WmsPickticketPage> wmsPickticketPages) {
		 Result<WmsPickticket> result = new Result<WmsPickticket>();
		 for (WmsPickticketPage wmsPickticketPage : wmsPickticketPages) {
		 WmsPickticket wmsPickticket = new WmsPickticket();
		 BeanUtils.copyProperties(wmsPickticketPage, wmsPickticket);
		 WmsPickticket wmsPickticketEntity = wmsPickticketService.getById(wmsPickticket.getId());
		 if(wmsPickticketEntity==null) {
			 result.error500("未找到对应实体");
		 }else {
			 boolean ok = wmsPickticketService.updateById(wmsPickticket);
			 wmsPickticketService.updateMain(wmsPickticket, wmsPickticketPage.getWmsPickticketDetailList());
			 result.success("修改成功!");
		 }
		 }

		 return result;
	 }
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@InterfaceLog(value = "出库单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			wmsPickticketService.delMain(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}

	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@InterfaceLog(value = "出库单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<WmsPickticket> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WmsPickticket> result = new Result<WmsPickticket>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wmsPickticketService.delBatchMain(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

	 /**
	  *   通过List对象删除
	  * @param wmsPickticketPages
	  * @return
	  */
	 @InterfaceLog(value = "出库单-通过json对象删除")
	 @DeleteMapping(value = "/deleteList")
	 public Result<?> deleteList(@RequestBody List<WmsPickticketPage> wmsPickticketPages) {
		 try {
			 for (WmsPickticketPage wmsPickticketPage : wmsPickticketPages) {
			 wmsPickticketService.delMain(wmsPickticketPage.getId());
			 }
		 } catch (Exception e) {
			 log.error("删除失败",e.getMessage());
			 return Result.error("删除失败!");
		 }
		 return Result.ok("删除成功!");
	 }

	 /**
	  *   通过json对象删除
	  * @param wmsPickticketPage
	  * @return
	  */
	 @InterfaceLog(value = "出库单-通过json对象删除")
	 @DeleteMapping(value = "/deleteJson")
	 public Result<?> deleteJson(@RequestBody WmsPickticketPage wmsPickticketPage) {
		 try {
				 wmsPickticketService.delMain(wmsPickticketPage.getId());
		 } catch (Exception e) {
			 log.error("删除失败",e.getMessage());
			 return Result.error("删除失败!");
		 }
		 return Result.ok("删除成功!");
	 }
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<WmsPickticket> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WmsPickticket> result = new Result<WmsPickticket>();
		WmsPickticket wmsPickticket = wmsPickticketService.getById(id);
		if(wmsPickticket==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wmsPickticket);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryWmsPickticketDetailByMainId")
	public Result<List<WmsPickticketDetail>> queryWmsPickticketDetailListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<WmsPickticketDetail>> result = new Result<List<WmsPickticketDetail>>();
		List<WmsPickticketDetail> wmsPickticketDetailList = wmsPickticketDetailService.selectByMainId(id);
		result.setResult(wmsPickticketDetailList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WmsPickticket wmsPickticket) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WmsPickticket> queryWrapper = QueryGenerator.initQueryWrapper(wmsPickticket, request.getParameterMap());
      List<WmsPickticket> queryList = wmsPickticketService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WmsPickticket> wmsPickticketList = new ArrayList<WmsPickticket>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wmsPickticketList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wmsPickticketList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WmsPickticketPage> pageList = new ArrayList<WmsPickticketPage>();
      for (WmsPickticket main : wmsPickticketList) {
          WmsPickticketPage vo = new WmsPickticketPage();
          BeanUtils.copyProperties(main, vo);
          List<WmsPickticketDetail> wmsPickticketDetailList = wmsPickticketDetailService.selectByMainId(main.getId());
          vo.setWmsPickticketDetailList(wmsPickticketDetailList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "出库单列表");
      mv.addObject(NormalExcelConstants.CLASS, WmsPickticketPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("出库单列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<WmsPickticketPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WmsPickticketPage.class, params);
              for (WmsPickticketPage page : list) {
                  WmsPickticket po = new WmsPickticket();
                  BeanUtils.copyProperties(page, po);
                  wmsPickticketService.saveMain(po, page.getWmsPickticketDetailList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
