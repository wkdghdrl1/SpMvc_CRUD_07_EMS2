package com.biz.ems.mapper;

import java.util.HashMap;

import org.apache.ibatis.jdbc.SQL;

public class BBsSQL {
	
	public String bbs_select_count_sql(final String search_option, final String keyword) {
		
		SQL sql = new SQL() {{
			SELECT("count(*)");
			FROM("tbl_board");
			if(search_option.equalsIgnoreCase("title")) {
				WHERE("title like #{keyword}");
			} else if (search_option.equalsIgnoreCase("writer")) {
				WHERE("writer like #{keyword}");
			} else if (search_option.equalsIgnoreCase("content")) {
				WHERE("content like #{keyword}");
			} else {
				WHERE("writer LIKE #{keyword}") ;
				OR();
				WHERE("title LIKE #{keyword}");
				OR();
				WHERE("content LIKE #{keyword}");
			}
		}};
		return sql.toString();
	}

	

	
	public String bbs_list_all(final HashMap<String, Object> option) {
		
		final String sort_option = option.get("sort_option").toString(); 
		
		final SQL main_sql = new SQL() {{
			SELECT("*");
			FROM(" tbl_board ");
			if( sort_option.equalsIgnoreCase("new")) {
				ORDER_BY("bno DESC");
			} else if(sort_option.equalsIgnoreCase("reply")) {
				ORDER_BY("replycnt DESC, bno DESC");
			} else if(sort_option.equalsIgnoreCase("view")) {
				ORDER_BY("viewcnt DESC, bno DESC") ;
			}
		}};
		
		SQL sql = new SQL() {{
			SELECT("*");
			FROM("( SELECT rownum AS rnum, A.* FROM( " + main_sql.toString() + ") A )") ;
			WHERE("rnum BETWEEN #{start} AND #{end}");
			
		}};
		return sql.toString();
		
	}
	
	
}

