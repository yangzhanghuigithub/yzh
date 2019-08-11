package com.learn.yzh.common.generate;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.learn.yzh.common.utils.FileUtils;
import com.learn.yzh.common.utils.FreeMarkers;
import com.learn.yzh.common.utils.StringUtilsEx;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

/**
 *
 */
public class MakeDBPersistence {


	private static Logger logger = LoggerFactory.getLogger(CodeGenerate.class);

	private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://192.168.0.147:3306/mplus?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&allowMultiQueries=true";
    

    private static final String USERNAME = "root";
    private static final String PASSWORD = "taihe1234";
    private static final String SCHEMA = "mplus"; // name of the database
    
    private static String OUT_DIR_MAPPER = "D:\\dao_generator\\mapper\\";
    private static String OUT_DIR_DOMAIN = "D:\\dao_generator\\domain\\";


    private static String FTL_DIR = "D:\\work\\remoteDesk\\learn_new\\learn_common\\src\\main\\java\\com\\learn\\common\\generate";
     
    // Mysql:1  Postgres:2  Oracle:3
    private static String DB = "1";
    
    // ${domain} : beauty table name
    // ${table} : table name
    // ${whereKeys}
    // ${updateColumns}
    // ${allColumns}
    // ${allValues}
    // ${paramKeys}
    
	public static void main(String[] args) {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			DatabaseMetaData metadata = connection.getMetaData();
			String catalog = connection.getCatalog();

			makeInterface(metadata, catalog);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static void makeInterface(DatabaseMetaData metadata, String catalog) {
		
		try {
			String[] types = { "TABLE" };
			String schema = "mplus"; // name of the database
			ResultSet tables = metadata.getTables(catalog, null, "%", types);
			// 获取文件分隔符
			String separator = File.separator;
			// 获取工程路径
			File projectPath = new DefaultResourceLoader().getResource("").getFile();
			while(!new File(projectPath.getPath()+separator+"src"+separator).exists()){
				projectPath = projectPath.getParentFile();
			}
			Configuration cfg = new Configuration();
			// 模板文件路径
			String tplPath = StringUtils.replace(projectPath+"/src/main/java/com/learn/common/generate/template", "/", separator);

			cfg.setDirectoryForTemplateLoading(new File(tplPath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		
			Template mapper = cfg.getTemplate("mapper.ftl");
			Template mapperXml = cfg.getTemplate("xmlMapper.ftl");
			Template domain = cfg.getTemplate("domain.ftl");
			
			/* 定义根目录*/
			Map<String, Object> root = new HashMap<String, Object> ();
			


			String tableName = "";
			
			while (tables.next()) {
				
				List <String> primaryKeys = new ArrayList<String>();
				Map <String, String> columnTypeMap = new HashMap<String, String>();
				
				tableName = tables.getString("TABLE_NAME");
				
				root.put("table", tableName.toLowerCase());
				
//				root.put("domain", tableName.toLowerCase());
				root.put("domain",StringUtilsEx.toCapitalizeCamelCase((beautyName(tableName.toLowerCase()))));
				
				root.put("limit", "#{limit}");
				
				ResultSet keys = metadata.getPrimaryKeys(null, null, tableName);
				
				StringBuilder whereKeys = new StringBuilder();
				
				while (keys.next()) {
					String key = keys.getString("COLUMN_NAME");
					whereKeys.append(key).append(" = ").append("#{");
					whereKeys.append(key).append("} and ");
					
					primaryKeys.add(key);
					
				}
				root.put("whereKeys", getOKString(whereKeys, "} and "));
				
				ResultSet columns = metadata
						.getColumns(null, null, tableName, null);
				
				StringBuilder allColumns = new StringBuilder();
				StringBuilder allvalues = new StringBuilder();
				StringBuilder updateColumns = new StringBuilder();
				StringBuilder updateColumns_ = null;
				
				List<ColumnInfo> objColumns = new ArrayList<ColumnInfo>();
				
				List<String> updateif = new ArrayList<String>();
				
				while (columns.next()) {
					String name = columns.getString("COLUMN_NAME");
					String type = columns.getString("TYPE_NAME");
//					int size = columns.getInt("COLUMN_SIZE");
					
					ColumnInfo columnInfo = new ColumnInfo();
					columnInfo.setName(name.toLowerCase());
					columnInfo.setProperty(StringUtilsEx.underlineToCamel(name));
					columnInfo.setType(getSqlType(type.toLowerCase()));
					objColumns.add(columnInfo);
					
					
					columnTypeMap.put(name, type);
					
					allColumns.append(name).append(", ");
					
					allvalues.append("#{");
					allvalues.append(StringUtilsEx.underlineToCamel(name)).append("}, ");
					
//				      <if test="companyClass != null" >
//				        company_class = #{companyClass,jdbcType=CHAR},
//				      </if>
//					<if test="companyClass" >
					updateColumns_ = new StringBuilder();
					//updateColumns_.append("<if test=\"").append(name + " != null\" >");
					updateColumns_.append("<if test=\"").append(getIfNull(type, name));
					updateColumns_.append(name).append(" = ").append("#{");
					updateColumns_.append(StringUtilsEx.underlineToCamel(name)).append("}, </if>");
					updateif.add(updateColumns_.toString());
					
					updateColumns.append(name).append(" = ").append("#{");
					updateColumns.append(StringUtilsEx.underlineToCamel(name)).append("}, ");
				}
//				String last = updateif.get(updateif.size() -1);
//				last = last.replace(", </if>", " </if>");
//				updateif.set(updateif.size() - 1, last);
				root.put("updateifColumns", updateif);
				
				root.put("allColumns", getOKString(allColumns, ","));
				root.put("ColumnInfos", objColumns);
				
				StringBuilder paramKeys = new StringBuilder();
				
				for (String k : primaryKeys) {
					String type = columnTypeMap.get(k);
					paramKeys.append(getSqlType(type.toLowerCase())).append(" ").append(k).append(", ");
					
				}
				
				root.put("paramKeys", getOKString(paramKeys, ","));
				
				root.put("allValues", getOKString(allvalues, "},"));
				
				root.put("updateColumns", getOKString(updateColumns, "},"));
				
//				Writer out = new OutputStreamWriter(System.out);
//				mapper.process(root, out);
				
				/////////////////
				root.put("orderByClause", "${orderByClause}");
				root.put("orderByClause_", "${_parameter}");
				root.put("item_key", "${item.key}");
				root.put("item_value", "${item.value}");
				/////////////////
				//writeFile();

				// 生成 Model
				String content = FreeMarkers.renderTemplate(mapper, root);
				String filePath =OUT_DIR_MAPPER + StringUtilsEx.toCapitalizeCamelCase(beautyName(tableName))  + "Dao.java";
				writeFile(content, filePath);
				logger.info("Model: {}", filePath);

				String xmlContent = FreeMarkers.renderTemplate(mapperXml, root);
				String xmlFilePath =OUT_DIR_MAPPER + StringUtilsEx.toCapitalizeCamelCase(beautyName(tableName)) + "Mapper.xml";
				writeFile(xmlContent, xmlFilePath);
				logger.info("Model: {}", xmlFilePath);
				
				System.out.println();

				String dominContent = FreeMarkers.renderTemplate(domain, root);
				String dominFilePath =OUT_DIR_DOMAIN + StringUtilsEx.toCapitalizeCamelCase(beautyName(tableName)) + ".java";
				writeFile(dominContent, dominFilePath);
				logger.info("Model: {}", dominFilePath);
//				out.flush();
				
				root.clear();
			}
		} catch (Exception e) {
		}
		
	}

	private static String beautyName(String tableName) {
		String rs = tableName.replaceFirst("t_", "").replaceFirst("app_", "").replaceFirst("o_", "");
		return rs;
	}
	private static String getOKString(StringBuilder sb, String chk) {
		String temp = sb.toString();
//		int length = temp.lastIndexOf("},");
		int length = temp.lastIndexOf(chk);

		if (length > -1) {
			if (",".equals(chk)) {
				return temp.substring(0, length);
			}
			return temp.substring(0, length + 1);
		}
		return sb.toString();
	}
	
	private static String getSqlType(String dbtype) {
		if(DB.equals("1")) {
			return getMySQLType(dbtype);
		} else if (DB.equals("2")) {
			return getPostgreSQLType(dbtype);
		} else {
			return "";
		}
	}
	/**
	 * PostgreSQL
	 * 
	 * @param dbtype
	 * @return
	 */
	private static String getPostgreSQLType(String dbtype) {
		
		String type = "";
		if ("varchar".equals(dbtype.toLowerCase())) {
			type =  "String";
		} else if ("decimal".equals(dbtype.toLowerCase())) {
			type =  "java.math.BigDecimal";
		} else if ("numeric".equals(dbtype.toLowerCase())) {
			type =  "java.math.BigDecimal";
		} else if ("int2".equals(dbtype.toLowerCase())) {
			type =  "java.lang.Short";
		} else if ("int4".equals(dbtype.toLowerCase())) {
			type =  "int";
		} else if ("int8".equals(dbtype.toLowerCase())) {
			type =  "long";
		} else if ("float4".equals(dbtype.toLowerCase())) {
			type =  "float";
		} else if ("float8".equals(dbtype.toLowerCase())) {
			type =  "double";
		} else if ("char".equals(dbtype.toLowerCase())) {
			type =  "java.util.Date";
		} else if ("timestamp".equals(dbtype.toLowerCase())) {
			type =  "java.sql.Timestamp";
		}
		 else if ("interval".equals(dbtype.toLowerCase())) {
				type =  "String";
			}  else if ("time".equals(dbtype.toLowerCase())) {
				type =  "java.sql.Time";
			}  else if ("boolean".equals(dbtype.toLowerCase())) {
				type =  "Boolean";
			}  else if ("bit".equals(dbtype.toLowerCase())) {
				type =  "String";
			}  else if ("float8".equals(dbtype.toLowerCase())) {
				type =  "double";
			} 
		
		return type;
	}
	
	/**
	 * MySQL
	 * 
	 * @param dbtype
	 * @return
	 */
    private static String getMySQLType(String dbtype) {
		
		String type = "";
		if ("char".equals(dbtype.toLowerCase())) {
			type =  "String";
		} else if ("varchar".equals(dbtype.toLowerCase())) {
			type =  "String";
		} else if ("timestamp".equals(dbtype.toLowerCase())) {
			type =  "java.util.Date";
		} else if ("bigint".equals(dbtype.toLowerCase())) {
			type =  "long";
		} else if ("bigint unsigned".equals(dbtype.toLowerCase())) {
			type =  "long";
		} else if ("SMALLINT".equals(dbtype.toUpperCase())) {
			type =  "int";
		} else if ("bit".equals(dbtype.toLowerCase())) {
			type =  "int";
		} else if ("TINYINT".equals(dbtype.toUpperCase())) {
			type =  "int";
		} else if ("MEDIUMINT".equals(dbtype.toUpperCase())) {
			type =  "int";
		} else if ("INT".equals(dbtype.toUpperCase())) {
			type =  "int";
		} else if ("FLOAT".equals(dbtype.toUpperCase())) {
			type =  "float";
		} else if ("DOUBLE".equals(dbtype.toUpperCase())) {
			type =  "double";
		} else if ("DECIMAL".equals(dbtype.toUpperCase())) {
			type =  "java.math.BigDecimal";
		} else if ("NUMERIC".equals(dbtype.toUpperCase())) {
			type =  "java.math.BigDecimal";
		} else if ("DATE".equals(dbtype.toUpperCase())) {
			type =  "java.util.Date";
		} else if ("DATETIME".equals(dbtype.toUpperCase())) {
			type =  "java.sql.Timestamp";
		} else if ("BOOLEAN".equals(dbtype.toUpperCase())) {
				type =  "Boolean";
		} else if ("DATETIME".equals(dbtype.toUpperCase())) {
			type =  "Date";
		}
		
		return type;
	}

	/**
	 * 将内容写入文件
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (FileUtils.createFile(filePath)){
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			}else{
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static String getIfNull(String dbtype, String name) {
		String type = "String";
		if(DB.equals("1")) {
			type = getMySQLType(dbtype);
		} else if (DB.equals("2")) {
			type =  getPostgreSQLType(dbtype);
		} else {

		}
/*
		if (type == "String") {
			return name + " != null\" >";
		} else if (type == "int"
				|| type == "float"
				|| type == "long") {
			return name + " != \" >";
		} else  {
			return name + " != null\" >";
		}*/
		return name + " != null\" >";

	}
}

