package org.fipecafi.tools.filter.component.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fipecafi.tools.filter.FilterVisitorAdapter;
import org.fipecafi.tools.filter.Option;
import org.fipecafi.tools.filter.QueryFilter;

public class FilterDBVisitor extends FilterVisitorAdapter{
	private Connection conn;
	public FilterDBVisitor(Connection conn){
		this.conn = conn;
	}
	@Override
	public void visit(QueryFilter filter) {
		String query = filter.getQuery();
		ResultSet rs = null;
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			List<Option> opts = new ArrayList<Option>();
			while(rs.next()){
				Option option = new Option();
				option.setId(rs.getInt(1));
				option.setName(rs.getString(2));
				opts.add(option);
			}
			conn.commit();
			filter.setOptions(opts);
		}catch(Exception e){
			try {
				if(conn!=null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
