package com.ylms.liuliangbao.config;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ylms.liuliangbao.utlis.HmacSHA256;
import com.ylms.liuliangbao.utlis.HttpUtil;
import com.ylms.liuliangbao.utlis.XXTea;

public class UserCoinInfo {
	// 时间戳毫秒
	public static Long timeStamp = System.currentTimeMillis();
	/** 接口参数加密 */
	public static String getParams(String mobile) {
		String params = "";
		try {
			params = XXTea.encrypt("mobile=" + mobile, "UTF-8",
					XXTea.toHex(Common.APP_SECRET.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
	/**
	 * 公共的参数和接口参数加密后形成的参数加密
	 */
	public static String getSign(String params) {
		return HmacSHA256.generateHmacSha256Signature(Common.APP_ID
				+ UserCoinInfo.timeStamp + Common.version + params,
				Common.SIGNSECRET);
	}
	
	@SuppressWarnings("unchecked")
	public static Integer getUserCoinInfo(String moblie,String accessToken) {
		Map<String, String> params = new HashMap<String, String>();
		// 获取流量币接口信息
		String mhttpUrl = "https://nb.189.cn/open/auth/getUserCoinInfo.do";
		// 开始构建请求参数
		String param = UserCoinInfo.getParams(moblie);
		String sign = UserCoinInfo.getSign(param);
		params.put("timeStamp", "" + UserCoinInfo.timeStamp);
		params.put("appId", Common.APP_ID);
		params.put("version", Common.version);
		params.put("params", param);
		params.put("accessToken", accessToken);
		params.put("sign", sign);
		String msg = HttpUtil.doPost(mhttpUrl, params);
		Integer availCount=0;
		if (msg != null) {
			// 转换成map格式
			Map<String,Object> map=JSON.parseObject(msg, Map.class);
            if(map.containsKey("availCount")){
            	for (Map.Entry<String, Object> s : map.entrySet()) {
            		if(s.getKey().equals("availCount")){
            			availCount=(Integer)s.getValue();
            		}
            	}
            }
		}
		return availCount;
	}
	
	
}
