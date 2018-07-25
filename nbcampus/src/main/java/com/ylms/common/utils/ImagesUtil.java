package com.ylms.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;

/**
 * 
 * 图片处理类
 * 
 * */
public class ImagesUtil {

	/**
	 * 
	 * 
	 * 图片进行64位解码保存
	 * 
	 * @param imagesUrl
	 *            图片的主机地址
	 * @param img
	 *            64位编码图片
	 */
	public static synchronized String getImageStr(String imagesUrl, String img) {
		Matcher matcher = Pattern.compile(",").matcher(img);
		String ImagesUrl = null;
		String logoImgUrl = "/" + System.currentTimeMillis();
		try {
			if (matcher.find()) {
				// 从首次出现的逗号处开始截取后面的字符串
				int a = matcher.start() + 1;
				// Base64解码
				byte[] b = Base64.decodeBase64(img.substring(a));
				FileOutputStream fos = new FileOutputStream(imagesUrl
						+ logoImgUrl + ".jpg");
				fos.write(b);// 将byte写入给定的路径下
				fos.close();
				ImagesUrl = logoImgUrl + ".jpg";
			}

			return ImagesUrl;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 
	 * 
	 * 图片进行64位解码保存
	 * 
	 * @param imagesUrl
	 *            图片的本地地址
	 * @param sssc64Images
	 *            64位编码图片
	 * @param tel
	 *            电话号码
	 * @param taskId
	 *            任务Id
	 */
	public static synchronized String getSsscImageName(String imagesUrl,
			String sssc64Images, String tel, String taskId) {
		Matcher matcher = Pattern.compile(",").matcher(sssc64Images);
		String iamgesHttp = null;
		try {
			if (matcher.find()) {
				// 从首次出现的逗号处开始截取后面的字符串
				int a = matcher.start() + 1;
				// Base64解码
				byte[] b = Base64.decodeBase64(sssc64Images.substring(a));
				FileOutputStream fos = new FileOutputStream(imagesUrl + taskId
						+ "+" + tel + ".jpg");
				fos.write(b);// 将byte写入给定的路径下
				fos.close();
				iamgesHttp = imagesUrl + taskId + "+" + tel + ".jpg";
			}
			return iamgesHttp;
		} catch (Exception e) {
			return iamgesHttp = null;
		}

	}

	// 删除指定文件夹下的图片
	public static synchronized boolean delAllFile(String path, String imgs) {
		boolean flag = false;
		String newImgs = "";
		if (path != null && !path.equals("")) {
			try {
				int i = imgs.lastIndexOf("/");
				newImgs = imgs.substring(i + 1);
			} catch (Exception e) {
				return flag;
			}
		}else{
			return false;
		}
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		File[] tempList = file.listFiles();
		for (File fli : tempList) {
			if (fli.getName().equals(newImgs)) {
				flag = fli.delete();
				break;
			}
		}
		return flag;
	}

}
