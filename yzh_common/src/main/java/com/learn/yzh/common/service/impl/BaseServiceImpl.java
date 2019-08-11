package com.learn.yzh.common.service.impl;


import com.learn.yzh.common.exception.DataException;
import com.learn.yzh.common.service.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service基础类<BR>
 *
 * @author wl
 */
public abstract class BaseServiceImpl<R, P> implements BaseService<R, P> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());



	/**
	 *
	 * @param param 参数
	 * @return 处理结果
	 */
	@Override
	public P service(R param) {
		logger.info("service 处理开始。");
		try {
			P p = doService(param);
			logger.info("service 处理结束。");
			return p;
		} catch (DataException e) {
			logger.error("service(): "+ e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error("service(): "+ e.getMessage(), e);
			throw new DataException(e);
		}

	}
	/**
	 * 具体业务的处理。
	 *
	 * @param param 传人参数
	 * @return 处理结果
	 */
	protected abstract P doService(R param) throws Exception;

}
