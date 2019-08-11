package com.learn.yzh.common.utils;

import java.sql.*;
import java.util.List;

public class JdbcUtil {

	public static String url = "jdbc:mysql://localhost:3306/wdj_devices?useUnicode=true&characterEncoding=utf-8";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String user = "root";
	public static String password = "1234";

	public Connection conn = null;
	public PreparedStatement pst = null;

	public JdbcUtil(String sql) {
		try {
			PropertiesLoader ps = new PropertiesLoader("application.properties");
			url = ps.getProperty("jdbc.url") ;
			driver = ps.getProperty("jdbc.driver") ;
			user = ps.getProperty("jdbc.username") ;
			password = ps.getProperty("jdbc.password") ;
			Class.forName(driver);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute() {
		try {
			this.pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量执行
	 * @param params
	 */
	public void batchExecute (List<Object[]> params) {
		try {
			conn.setAutoCommit(false);
			for (int i = 0; i < params.size(); i++) {
				int index = 1 ;
				Object[] obj = params.get(i) ;
				for (int j = 0; j < obj.length; j++) {
					pst.setObject(index++, obj[j]) ;
				}
				pst.addBatch() ;
			}
			pst.executeBatch() ;
			conn.commit() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (this.pst != null) this.pst.close();
			if (this.conn != null) this.conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
