package org.jeecg.modules.demo.orderMain.controller;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.orderMain.entity.TestOrderProduct;
import org.jeecg.modules.demo.orderMain.entity.TestOrderMain;
import org.jeecg.modules.demo.orderMain.vo.TestOrderMainPage;
import org.jeecg.modules.demo.orderMain.service.ITestOrderMainService;
import org.jeecg.modules.demo.orderMain.service.ITestOrderProductService;
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
 * @Description: 测试订单主表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@RestController
@RequestMapping("/orderMain/testOrderMain")
@Slf4j
//@Api(tags="testOrderMain")
public class TestOrderMainController {
	@Autowired
	private ITestOrderMainService testOrderMainService;
	@Autowired
	private ITestOrderProductService testOrderProductService;
	
	/**
	  * 分页列表查询
	 * @param testOrderMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
//	@ApiOperation(value = "获取TestOrderMain数据列表", notes = "获取所有TestOrderMain数据列表")
	public Result<IPage<TestOrderMain>> queryPageList(TestOrderMain testOrderMain,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<TestOrderMain>> result = new Result<IPage<TestOrderMain>>();
		QueryWrapper<TestOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(testOrderMain, req.getParameterMap());
		Page<TestOrderMain> page = new Page<TestOrderMain>(pageNo, pageSize);
		IPage<TestOrderMain> pageList = testOrderMainService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param testOrderMainPage
	 * @return
	 */
	@InterfaceLog(value = "测试订单主表-添加")
	@PostMapping(value = "/add")
//	@ApiOperation(value = "添加TestOrderMainPage", notes = "添加TestOrderMainPage")
	public Result<TestOrderMain> add(@RequestBody TestOrderMainPage testOrderMainPage) {
		Result<TestOrderMain> result = new Result<TestOrderMain>();
		try {
			TestOrderMain testOrderMain = new TestOrderMain();
			BeanUtils.copyProperties(testOrderMainPage, testOrderMain);
			
			testOrderMainService.saveMain(testOrderMain, testOrderMainPage.getTestOrderProductList());
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
    	  *   批量添加
    	 * @param testOrderMainPage
    	 * @return
    	 */
    	@InterfaceLog(value = "测试订单主表-添加")
    	@PostMapping(value = "/addList")
//    	@ApiOperation(value = "批量添加TestOrderMainPage", notes = "批量添加TestOrderMainPage")
    	public Result<TestOrderMain> addList(@RequestBody List<TestOrderMainPage> testOrderMainPages) {
    		Result<TestOrderMain> result = new Result<TestOrderMain>();
    		try {
    		for(TestOrderMainPage testOrderMainPage: testOrderMainPages ){
    			TestOrderMain testOrderMain = new TestOrderMain();
    			BeanUtils.copyProperties(testOrderMainPage, testOrderMain);

    			testOrderMainService.saveMain(testOrderMain, testOrderMainPage.getTestOrderProductList());
    			result.success("添加成功！");
    			}
    		} catch (Exception e) {
    			log.error(e.getMessage(),e);
    			result.error500("操作失败");
    		}
    		return result;
    	}
	
	/**
	  *  编辑
	 * @param testOrderMainPage
	 * @return
	 */
	@InterfaceLog(value = "测试订单主表-编辑")
	@PutMapping(value = "/edit")
//	@ApiOperation(value = "编辑TestOrderMainPage", notes = "编辑TestOrderMainPage")
	public Result<TestOrderMain> edit(@RequestBody TestOrderMainPage testOrderMainPage) {
		Result<TestOrderMain> result = new Result<TestOrderMain>();
		TestOrderMain testOrderMain = new TestOrderMain();
		BeanUtils.copyProperties(testOrderMainPage, testOrderMain);
		TestOrderMain testOrderMainEntity = testOrderMainService.getById(testOrderMain.getId());
		if(testOrderMainEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = testOrderMainService.updateById(testOrderMain);
			testOrderMainService.updateMain(testOrderMain, testOrderMainPage.getTestOrderProductList());
			result.success("修改成功!");
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@InterfaceLog(value = "测试订单主表-通过id删除")
	@DeleteMapping(value = "/delete")
//	@ApiOperation(value = "通过ID删除TestOrderMainPage", notes = "通过ID删除TestOrderMainPage")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			testOrderMainService.delMain(id);
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
	@InterfaceLog(value = "测试订单主表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
//	@ApiOperation(value = "批量删除TestOrderMainPage", notes = "批量删除TestOrderMainPage")
	public Result<TestOrderMain> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<TestOrderMain> result = new Result<TestOrderMain>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.testOrderMainService.delBatchMain(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
//	@ApiOperation(value = "通过ID查询TestOrderMain", notes = "通过ID查询TestOrderMain")
	public Result<TestOrderMain> queryById(@RequestParam(name="id",required=true) String id) {
		Result<TestOrderMain> result = new Result<TestOrderMain>();
		TestOrderMain testOrderMain = testOrderMainService.getById(id);
		if(testOrderMain==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(testOrderMain);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTestOrderProductByMainId")
//	@ApiOperation(value = "通过ID查询TestOrderProduct", notes = "通过ID查询TestOrderProduct")
	public Result<List<TestOrderProduct>> queryTestOrderProductListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<TestOrderProduct>> result = new Result<List<TestOrderProduct>>();
		List<TestOrderProduct> testOrderProductList = testOrderProductService.selectByMainId(id);
		result.setResult(testOrderProductList);
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
  public ModelAndView exportXls(HttpServletRequest request, TestOrderMain testOrderMain) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TestOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(testOrderMain, request.getParameterMap());
      List<TestOrderMain> queryList = testOrderMainService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<TestOrderMain> testOrderMainList = new ArrayList<TestOrderMain>();
      if(oConvertUtils.isEmpty(selections)) {
    	  testOrderMainList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  testOrderMainList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<TestOrderMainPage> pageList = new ArrayList<TestOrderMainPage>();
      for (TestOrderMain main : testOrderMainList) {
          TestOrderMainPage vo = new TestOrderMainPage();
          BeanUtils.copyProperties(main, vo);
          List<TestOrderProduct> testOrderProductList = testOrderProductService.selectByMainId(main.getId());
          vo.setTestOrderProductList(testOrderProductList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "测试订单主表列表");
      mv.addObject(NormalExcelConstants.CLASS, TestOrderMainPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("测试订单主表列表数据", "导出人:Jeecg", "导出信息"));
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
              List<TestOrderMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TestOrderMainPage.class, params);
              for (TestOrderMainPage page : list) {
                  TestOrderMain po = new TestOrderMain();
                  BeanUtils.copyProperties(page, po);
                  testOrderMainService.saveMain(po, page.getTestOrderProductList());
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
