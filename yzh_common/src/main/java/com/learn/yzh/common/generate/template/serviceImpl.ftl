package ${packageName}.service.${moduleName}.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${packageName}.dao.${moduleName}.${ClassName}Dao;
import ${packageName}.model.${moduleName}.${ClassName};
import com.learn.order.service.${moduleName}.${ClassName}Service;

/**
 * ${functionName}ServiceImpl
 * @author ${classAuthor}
 * @version ${classVersion}
 */
@Service("${className}Service")
public class ${ClassName}ServiceImpl extends BaseServiceImpl<${ClassName}, String> implements ${ClassName}Service {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(${ClassName}Service.class);
	
	@Autowired
	private ${ClassName}Dao ${className}Dao;
	

	
}
