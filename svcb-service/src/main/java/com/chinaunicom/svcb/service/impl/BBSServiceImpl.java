package com.chinaunicom.svcb.service.impl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinaunicom.svcb.dao.BbsModuleDao;
import com.chinaunicom.svcb.model.BbsModule;
import com.chinaunicom.svcb.service.BBSService;

@Service
public class BBSServiceImpl implements BBSService {
 
	@Autowired
	BbsModuleDao moduleDao;
 
	@Autowired
	SQLManager sql ;
 
	@Override
	public String queryDemo(String user) {
		List<BbsModule> getList=(List<BbsModule>) sql.select("bbsModule.sample", BbsModule.class);
		System.out.println("=====queryDemo====getList==============="+JSON.toJSON(getList));
		return JSON.toJSON(getList).toString();
	}

 
}
