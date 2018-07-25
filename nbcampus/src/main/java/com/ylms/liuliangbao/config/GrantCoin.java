package com.ylms.liuliangbao.config;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ylms.liuliangbao.utlis.HmacSHA256;
import com.ylms.liuliangbao.utlis.HttpUtil;
import com.ylms.liuliangbao.utlis.XXTea;

/**
 * 派币接口
 * 
 * */
public class GrantCoin {
     // 时间戳毫秒
 	public static Long timeStamp = System.currentTimeMillis();
 	
	/** 接口参数加密 */
	public static String getParams(String mobile,int coin,int taskId) {
		String params = "";
		try {
			params = XXTea.encrypt("requestNo="+GrantCoin.getRequestNo()+"&mobile=" + mobile+"&coin="+coin+"&taskId="+taskId, "UTF-8",
					XXTea.toHex(Common.APP_SECRET.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
	
	/**
	 * 生成客户端请求流水号
	 * 
	 * */
	public static String getRequestNo(){
		String requestNo=""+System.currentTimeMillis()+generateRandomNumber(5);
		return requestNo;
	}
	
	/** 
     * 产生5位随机数 
     * @return 
     */  
    protected static long generateRandomNumber(int n){  
        if(n<1){  
            throw new IllegalArgumentException("随机数位数必须大于0");  
        }  
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);  
    }  
	/**
	 * 公共的参数和接口参数加密后形成的参数进行加密
	 */
	public static String getSign(String params) {
		return HmacSHA256.generateHmacSha256Signature(Common.APP_ID
				+ GrantCoin.timeStamp + Common.version + params,
				Common.SIGNSECRET);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getGrantCoin(String mobile,int coin,int taskId) {
		Map<String, String> params = new HashMap<String, String>();
		// 派币接口
		String mhttpUrl = "https://nb.189.cn/open/auth/grantCoin.do";
		// 开始构建请求参数
		String param = GrantCoin.getParams(mobile,coin,taskId);
		String sign = GrantCoin.getSign(param);
		params.put("timeStamp", "" + GrantCoin.timeStamp);
		params.put("appId", Common.APP_ID);
		params.put("version", Common.version);
		params.put("params", param);
		params.put("sign", sign);
		String msg = HttpUtil.doPost(mhttpUrl, params);
		//Map<String,Object> map =null;
		
		Map<String,Object> map =new HashMap<String, Object>();
//		map.put("result", 221310000);
//		map.put("orderNo",""+getRequestNo());
//		map.put("requestNo", ""+getRequestNo());
//		map.put("availableNum", 0);
		
		if (msg != null) {
			// 转换成map格式
			map=JSON.parseObject(msg, Map.class);
		}
		return map;
	}
	
	
	public static void main(String[] args) {
//		Map<String, String> params = new HashMap<String, String>();
//		// 派币接口
//		String mhttpUrl = "https://nb.189.cn/open/auth/grantCoin.do";
//		// 开始构建请求参数
//		String param = GrantCoin.getParams("15899629797",10,100007794);
//		String sign = GrantCoin.getSign(param);
//		params.put("timeStamp", "" + GrantCoin.timeStamp);
//		params.put("appId", Common.APP_ID);
//		params.put("version", Common.version);
//		params.put("params", param);
//		params.put("sign", sign);
//		String msg = HttpUtil.doPost(mhttpUrl, params);	
//		Map<String,Object> map =new HashMap<String, Object>();
//		if (msg != null) {
//			// 转换成map格式
//			map=JSON.parseObject(msg, Map.class);
//            if(map.containsKey("result")){
//            	System.out.println("访问成功，返回信息:");
//            	for (Map.Entry<String, Object> s : map.entrySet()) {
//				System.out.println(s.getKey()+":"+s.getValue());
//				}
//            }
//		}
	}
}
