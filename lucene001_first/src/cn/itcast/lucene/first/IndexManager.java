package cn.itcast.lucene.first;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import cn.itcast.lucene.dao.BookDaoImpl;
import cn.itcast.lucene.po.Book;

/**
 * 
 * <p>
 * Title: IndexManager
 * </p>
 * <p>
 * Description:索引维护
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智播客
 * @date 2020-12-12
 * @version 1.0
 */
public class IndexManager {

	// 创建索引
	@Test
	public void createIndexFromDb() throws Exception {

		// 一个document对应一条book表的记录
		// 定义List存储document
		List<Document> docs = new ArrayList<Document>();
		// 调用dao获取book数据
		List<Book> bookList = new BookDaoImpl().findBookAll();
		for (Book book : bookList) {
			// 创建Document
			Document doc = new Document();

			// 创建Field域
			// 图书id的field
			// 参数：域名、field中存储的value值，是否存储
			// TextField id = new TextField("id", book.getId().toString(),
			// Store.YES);
			// //图书名称的field
			// TextField name = new TextField("name", book.getName(),
			// Store.YES);
			// //图书价格
			// FloatField price = new FloatField("price", book.getPrice(),
			// Store.YES);
			// //图书图片
			// TextField pic = new TextField("pic", book.getPic(), Store.YES);
			// //图书的描述
			// TextField description = new TextField("description",
			// book.getDescription(), Store.YES);

			// 根据图书字段性质修改成正确的Field的类型

			// 图书id，不要分词，要索引、要存储
			StringField id = new StringField("id", book.getId().toString(),
					Store.YES);

			// 图书名称：要分词，要索引，要存储
			TextField name = new TextField("name", book.getName(), Store.YES);
			// 图书价格，要分词，要索引，要存储
			FloatField price = new FloatField("price", book.getPrice(),
					Store.YES);

			// 图书图片,不要分词，不要索引，要存储
			StoredField pic = new StoredField("pic", book.getPic());

			// 图书描述，要分词，要索引，不要存储
			TextField description = new TextField("description",
					book.getDescription(), Store.NO);

			// 将field加入document中
			doc.add(id);
			doc.add(name);
			doc.add(price);
			doc.add(pic);
			doc.add(description);

			// 将一个doc加入List<document>中
			docs.add(doc);

		}

		// 创建分词器
		Analyzer analyzer = new StandardAnalyzer();

		// 创建索引目录 的流对象,指定索引目录 的位置
		Directory d = FSDirectory.open(new File(
				"F:\\develop\\lucene\\indexdata"));

		// IndexWriter配置对象
		// 参数：lucene的版本，分词器
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);

		// 创建索引操作对象，提供了很多方法操作索引文件，（添加、修改、删除）
		IndexWriter indexWriter = new IndexWriter(d, conf);

		// 通过indexWriter创建索引
		for (Document doc : docs) {
			// 创建索引
			indexWriter.addDocument(doc);
		}
		// 提交
		indexWriter.commit();
		// 关闭资源
		indexWriter.close();

	}

	// 删除索引
	@Test
	public void deleteIndex() throws IOException, ParseException {

		// 调用indexWriter中删除索引方法

		// 创建分词器
		Analyzer analyzer = new StandardAnalyzer();

		// 创建索引目录 的流对象,指定索引目录 的位置
		Directory d = FSDirectory.open(new File(
				"F:\\develop\\lucene\\indexdata"));

		// IndexWriter配置对象
		// 参数：lucene的版本，分词器
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);

		// 创建indexWriter
		IndexWriter indexWriter = new IndexWriter(d, conf);

		// 删除全部索引
		// indexWriter.deleteAll();

		// 查询分析器
		// 第一个参数：指定默认搜索的域,第二个：分词器
		QueryParser queryParser = new QueryParser("description", analyzer);

		// 创建查询对象
		Query query = queryParser.parse("description:java");
		// 删除符合条件的索引，删除符合query查询的所有document
		indexWriter.deleteDocuments(query);
		// 提交
		indexWriter.commit();
		// 关闭资源
		indexWriter.close();

	}

	// 更新索引
	@Test
	public void updateIndex() throws IOException {
		// 调用indexWriter中更新索引方法
		// 创建分词器
		Analyzer analyzer = new StandardAnalyzer();

		// 创建索引目录 的流对象,指定索引目录 的位置
		Directory d = FSDirectory.open(new File(
				"F:\\develop\\lucene\\indexdata"));

		// IndexWriter配置对象
		// 参数：lucene的版本，分词器
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);

		// 创建indexWriter
		IndexWriter indexWriter = new IndexWriter(d, conf);

		// 创建一个term作为查询条件
		// 参数：field域名称，值
		Term term = new Term("id", "1");

		// 创建document，新的document，替换id等的1的document
		Document doc = new Document();
		// 图书id，不要分词，要索引、要存储
		StringField id = new StringField("id", "1".toString(),
				Store.YES);

		// 图书名称：要分词，要索引，要存储
		TextField name = new TextField("name", "java编程思想第三版", Store.YES);
		
		doc.add(id);
		doc.add(name);

		// 更新思路：根据term的信息查询document，找到之后删除document，添加doc中的添加到索引库
		// 总之：先查询、再删除、再添加，建议根据主键field查询document。
		indexWriter.updateDocument(term, doc);
		//提交
		indexWriter.commit();
		//关闭资源
		indexWriter.close();
	}

}
