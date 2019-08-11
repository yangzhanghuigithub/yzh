package com.learn.yzh.common.model.movie.cache;
import com.learn.yzh.common.model.movie.MovieHall;
import com.learn.yzh.common.utils.StringUtils;

/**
 * 影厅缓存类
 * @author Lomis
 *
 */
@SuppressWarnings("unchecked")
public class HallCache extends CacheObject {
	
	private static HallCache _instance;

	/**
	 * 获取创建对象
	 * @return
	 */
	public static HallCache getInstance () {
		if (_instance == null) {
			_instance = new HallCache();
		}
		return _instance;
	}

	@Override
	public String getCacheName() {
		return getRedisConstants().getHall();
	}

	private void putModel(MovieHall h) {
		getRedis().hset(getCacheName(),h.getCinemaCode() + "_" + h.getHallCode(),h);
		
	}
	
	/**
	 * 获取单个影厅详情
	 * @param filmCode
	 * @return
	 */
	public MovieHall getCachePojo (String cinemaCode, String hallCode) {
		MovieHall hall = null;
		if (StringUtils.isNotBlank(cinemaCode) && StringUtils.isNotBlank(hallCode)) {
//			hall = JsonMapper.getInstance().fromJson(_data.get(cinemaCode + "_" + hallCode), Hall.class);
			hall = getRedis().hget(getCacheName(),cinemaCode + "_" + hallCode,MovieHall.class);
		}
		return hall;
	}
	
	public void syncHallCache(MovieHall h) {
		putModel(h);
//		this.setObject(h.getHallCode(), _data);
	}

}
