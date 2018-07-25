package com.ylms.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ylms.common.entity.Dict;
import com.ylms.common.entity.Prefecture;
import com.ylms.common.entity.Province;
import com.ylms.common.entity.School;
import com.ylms.service.impl.IDictDao;
import com.ylms.service.impl.IPrefectureDao;
import com.ylms.service.impl.IProvinceDao;
import com.ylms.service.impl.ISchoolDao;

/**
 * 系统字典处理工具类
 * 
 * @author JQS
 */
public class DictUtil {
	private static final Logger log = LoggerFactory.getLogger(DictUtil.class);

	public static void rereshDict(ServletContext sc) {
		log.info("................正在加载字典数据................");
		// 获取web应用中的spring容器
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		IDictDao basedao = (IDictDao) ac.getBean("dictDao");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("useFlag", 1);
		List<Dict> list = basedao.list(param);
		// <字典名称,<字典key,字典value>>
		Map<String, Map<String, String>> allMap = new HashMap<String, Map<String, String>>();
		// 循环字典列表，按照字典名称对所有字典进行分类
		for (Dict d : list) {
			String dictName = d.getDictName();
			Map<String, String> m = allMap.get(dictName);
			if (m != null) {
				m.put(d.getCkey(), d.getCvalue());
			} else {
				// 有顺序的map
				m = new LinkedHashMap<String, String>();
				m.put(d.getCkey(), d.getCvalue());
				allMap.put(dictName, m);
			}
		}
		sc.setAttribute("dict", allMap);
		// 循环字典并把按字典名称放到应用上下文
		for (String dn : allMap.keySet()) {
			//log.info(dn + " :" + allMap.get(dn));
			sc.setAttribute(dn, allMap.get(dn));
		}
	}

	/**
	 * 把省份数据加载放到上下文中
	 * 
	 * @param sc
	 */
	public static void reProvince(ServletContext sc) {
		log.info("..............正在加载省份数据..............");
		Map<String, Object> param = new HashMap<String, Object>();
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		IProvinceDao iprovincedao = (IProvinceDao) ac.getBean("provinceDao");
		List<Province> list = iprovincedao.list(param);
		// Map<deptId,deptName>
		Map<String, String> provinceMap = new HashMap<String, String>();
		for (Province r : list) {
			provinceMap.put(r.getId() + "", r.getProvinceName());
			//log.info("[省份id:" + r.getId() + "\tprovinceName:"+ r.getProvinceName() + "]");
		}
		sc.setAttribute("provinceMap", provinceMap);
	}
	
	
	/**
	 * 把专区字典数据加载放到上下文中
	 * 
	 * @param sc
	 */
	public static void rePrefecture(ServletContext sc) {
		log.info("................正在加载专区字典数据.................");
		Map<String, Object> param = new HashMap<String, Object>();
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		IPrefectureDao iprefecturedao = (IPrefectureDao) ac.getBean("prefectureDao");
		List<Prefecture> list = iprefecturedao.list(param);
		// Map<deptId,deptName>
		Map<String, String> prefecture = new HashMap<String, String>();
		for (Prefecture r : list) {
			prefecture.put(r.getId() + "", r.getName());
			//log.info("[专区id:" + r.getId() + "\t专区名:"+ r.getName() + "]");
		}
		sc.setAttribute("prefectureMap", prefecture);
	}

	/**
	 * 把高校数据加载放到上下文中
	 * 
	 * @param sc
	 */
	public static void reSchool(ServletContext sc) {
		log.info("..................正在加载学校字典数据....................");
		Map<String, Object> param = new HashMap<String, Object>();
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		ISchoolDao sch = (ISchoolDao) ac.getBean("schoolDao");
		List<School> list = sch.list(param);
		List<Object> maxList = new ArrayList<Object>();
		Set<String> schoolKey = new HashSet<String>();
		for (School s : list) {
			// 添加首写字母,同时去重！
			boolean flag=false;
			for (String key : schoolKey) {
				if(key.equals(s.getSchoolKey())){
					flag=true;
				}
			}
			if(!flag){
				schoolKey.add(s.getSchoolKey());
			}
		}
		
		for (String ckey : schoolKey) {
			  //保证是新的List，避免被覆盖掉
			List<Map<String, String>> minList = new ArrayList<Map<String, String>>();
			for (School r : list) {
				if (r.getSchoolKey().equals(ckey)) {
					Map<String, String> minMap = new HashMap<String, String>();
//					log.info("搜索键ckey"+ckey+"+[学校ID:" + r.getId() + "\t搜索字母:"
//							+ r.getSchoolKey() + "\t学校名称:" + r.getSchoolName()
//							+ "]");
					minMap.put("name", r.getSchoolName());
					minMap.put("value", r.getId().toString());
					minList.add(minMap);
				}
			}
            //保证是新的Map，避免被覆盖掉
			Map<String, Object> mMap = new HashMap<String, Object>();
			mMap.put("name", ckey);
			mMap.put("items", minList);
			maxList.add(mMap);
		}
		
		sc.setAttribute("schoolDict", maxList);
		
		//预留供PC页面展示学校名
		Map<String, Object> map= new HashMap<String, Object>();
		for (School school : list) {
			map.put(""+school.getId(), school.getSchoolName());
		}
		sc.setAttribute("schoolMap", map);
	}
}
