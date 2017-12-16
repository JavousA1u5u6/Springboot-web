package cn.itcast.lucene.dao;

import java.util.List;

import cn.itcast.lucene.po.Book;

/**
 * 
 * <p>Title: BookDao</p>
 * <p>Description:图书的dao接口 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智播客
 * @date	2020-12-12
 * @version 1.0
 */
public interface BookDao {
	
	//查询book表中所有的记录
	public List<Book> findBookAll()throws Exception;

}
