package com.ylms.common.timer;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ylms.mysqlcall.RedisDataUtils;
import com.ylms.common.entity.Prefecture;
import com.ylms.common.entity.Task;
import com.ylms.common.entity.TaskCount;
import com.ylms.common.utils.DataToRedis;
import com.ylms.service.impl.IPrefectureDao;
import com.ylms.service.impl.IRedisServiceImplDao;
import com.ylms.service.impl.ITaskCountDao;
import com.ylms.service.impl.ITaskDao;

//*"0/10 * * * * ?" 每10秒触发   
//*"0 0 12 * * ?" 每天中午12点触发   
//*"0 15 10 ? * *" 每天上午10:15触发   
//*"0 15 10 * * ?" 每天上午10:15触发   
//*"0 15 10 * * ? *" 每天上午10:15触发   
//*"0 15 10 * * ? 2005" 2005年的每天上午10:15触发   
//*"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发   
//*"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发   
//*"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发   
//*"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发   
//*"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发   
//*"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发   
//*"0 15 10 15 * ?" 每月15日上午10:15触发   
//*"0 15 10 L * ?" 每月最后一日的上午10:15触发   
//*"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发   
//*"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发   
//*"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发   
//*每隔5秒执行一次：*/5 * * * * ?   
//*每隔1分钟执行一次：0 */1 * * * ?   
//*每天23点执行一次：0 0 23 * * ?   
//*每天凌晨1点执行一次：0 0 1 * * ?   
//*每月1号凌晨1点执行一次：0 0 1 1 * ?   
//*每月最后一天23点执行一次：0 0 23 L * ?   
//*每周星期天凌晨1点实行一次：0 0 1 ? * L   
//*在26分、29分、33分执行一次：0 26,29,33 * * * ?   
//*每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ? 

/**
 * 定时任务添加数据 定时调用存储过程
 * 
 **/
@Component
public class ToTimer {
	@Autowired
	private IRedisServiceImplDao rsd;
	@Autowired
	private ITaskCountDao itaskcountdao;
	@Autowired
	private IPrefectureDao iprefecturedao;
	@Autowired
	private ITaskDao itaskdao;
	@Autowired
	private DataToRedis dataToRedis;
	
	private static final Logger log = LoggerFactory.getLogger(ToTimer.class);

	// 每隔300秒调用一次
	/**
	 * 调用存储过程。(任务列表日汇总,同时也修改任务列表汇总)
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void run() {
		// 获取redis里对应的value
		List<Map<String, Object>> obj = RedisDataUtils.getList(dataToRedis.getDbClick());
		if (obj.size() > 0) {
			for (Map<String, Object> object : obj) {
				for (Map.Entry<String, Object> mp : object.entrySet()) {
					if (mp.getValue() instanceof TaskCount) {
						TaskCount t = (TaskCount) mp.getValue();
						log.info("**********获取到redis的" + dataToRedis.getDbClick()
								+ "号数据库的数据********");
						itaskcountdao.updateDayCountAndTaskCount(t);
						log.info("**********任务专区ID:" + t.getPrefectureId()
								+ "下的任务" + t.getTaskId() + "已写入mysql");
					}
				}
			}

		} else {
			log.info("**********获取到redis的" + dataToRedis.getDbClick()
					+ "号数据库的数据为空!!********");
		}

	}

	// 每隔300秒调用一次
	/**
	 * 调用存储过程。(修改任务完成数)
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void run1() {
		// 获取redis里对应的value
		List<Map<String, Object>> obj = RedisDataUtils.getList(dataToRedis
				.getDbComplete());
		if (obj.size() > 0) {
			for (Map<String, Object> object : obj) {
				for (Map.Entry<String, Object> mp : object.entrySet()) {
					if (mp.getValue() instanceof TaskCount) {
						TaskCount t = (TaskCount) mp.getValue();
						log.info("**********获取到redis的" + dataToRedis.getDbComplete()
								+ "号数据库的数据********");
						itaskcountdao.updateTaskSsscnumber(new TaskCount(t
								.getPrefectureId(), t.getTaskId()));
						log.info("**********任务专区ID:" + t.getPrefectureId()
								+ "下的任务" + t.getTaskId() + "已写入mysql");
					}
				}
			}
		} else {
			log.info("**********获取到redis的" + dataToRedis.getDbComplete()
					+ "号数据库的数据为空!!********");
		}

	}

	// 每隔300秒调用一次
	/**
	 * 调用存储过程。(任务列表截图数)
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void run2() {
		// 获取redis里对应的value
		List<Map<String, Object>> obj = RedisDataUtils.getList(dataToRedis
				.getDbSsscImages());
		if (obj.size() > 0) {
			for (Map<String, Object> object : obj) {
				for (Map.Entry<String, Object> mp : object.entrySet()) {
					if (mp.getValue() instanceof TaskCount) {
						TaskCount t = (TaskCount) mp.getValue();
						log.info("**********获取到redis的"+dataToRedis
								.getDbSsscImages()+"号数据库的数据********");
						itaskcountdao.updateTaskSsscnumber(new TaskCount(t
								.getPrefectureId(), t.getTaskId()));
						log.info("**********任务专区ID:" + t.getPrefectureId()
								+ "下的任务" + t.getTaskId() + "已写入mysql");
					}
				}
			}
		} else {
			log.info("**********获取到redis的"+dataToRedis
					.getDbSsscImages()+"号数据库的数据为空!!********");
		}

	}

	/**
	 * 调用存储过程。(每天1：00分更新任务列表日汇总)
	 * 
	 * 
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	// @Scheduled(cron = "0/50 * * * * ?")
	public void updateDayCount() {
		// 找到所有在线专区
		List<Prefecture> p = iprefecturedao.findAllByOnlie();
		if (p.size() > 0) {
			for (Prefecture prefecture : p) {
				// 根据专区ID找到所属的在线任务列表
				List<Task> task = itaskdao.listById(prefecture.getId());
				if (task.size() > 0) {
					for (Task task1 : task) {
						// 调用存储过程,初始化在线任务列表日汇总
						itaskcountdao.addDayCount(new TaskCount(prefecture
								.getId(), task1.getId()));
					}
					log.info("初始化任务列表的日汇总完成....");
				} else {
					log.info("任务专区下没有要进行初始化的任务列表....");
				}
			}
		} else {
			log.info("没有任务专区要进行初始化....");
		}

	}
}
