package com.learn.yzh.common.model.movie.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.learn.yzh.common.constants.RedisConstants;
import com.learn.yzh.common.model.movie.MovieSeat;
import com.learn.yzh.common.utils.JsonMapper;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;


/**
 * Created by wl on 2018/5/7.
 */
public class SeatCache extends CacheObject {

	private static SeatCache _instance;

	/**
	 * 获取创建对象
	 * @return
	 */
	public static SeatCache getInstance () {
		if (_instance == null) {
			_instance = new SeatCache();
		}
		return _instance;
	}
	@Override
	public String getCacheName() {
		return getRedisConstants().getSeat();
	}
	/**
	 * 查询影厅座位列表
	 * @param hallCode
	 * @return
	 */
	public List<MovieSeat> getSeats(String cinemaCode, String hallCode) {
		List<MovieSeat> ss = null;
		String jsonString = getRedisData(cinemaCode + "_" + hallCode);
		if (StringUtils.isNotBlank(jsonString)) {
			JavaType javaType = JsonMapper.getInstance().createCollectionType(List.class, MovieSeat.class);
			ss = JsonMapper.getInstance().fromJson(jsonString, javaType);
		}
		return ss;
	}

	/**
	 * 更新场次座位图状态
	 * @param cinemaCode
	 * @param map
	 */
	public void updateShowSeatState(String cinemaCode, Map<String, String> map) {
		if (map != null && map.size() > 0) {
			setObject(cinemaCode, map);
		}
		
	}
	/**
	 * 同步座位缓存
	 * @param cinemaCode
	 * @param hallNo
	 * @param seats
	 */
	public void syncSeats(String cinemaCode, String hallNo, List<MovieSeat> seats) {
		setObject(cinemaCode + "_" + hallNo, seats);
	}
	
}
