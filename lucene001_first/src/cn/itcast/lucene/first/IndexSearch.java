package cn.itcast.lucene.first;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

/**
 * 
 * <p>
 * Title: IndexSearch
 * </p>
 * <p>
 * Description:搜索索引
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智播客
 * @date 2020-12-13
 * @version 1.0
 */
public class IndexSearch {

	// 搜索索引
	@Test
	public void searchIndex() throws ParseException, IOException {

		// 分词,搜索过程使用的分词器要和索引时使用的分词器一致
		Analyzer analyzer = new StandardAnalyzer();

		// 查询分析器
		// 第一个参数：指定默认搜索的域,第二个：分词器
		QueryParser queryParser = new QueryParser("description", analyzer);

		// 创建查询对象
		Query query = queryParser.parse("description:java");

		// 创建索引目录 的流对象,指定索引目录 的位置
		Directory d = FSDirectory.open(new File(
				"F:\\develop\\lucene\\indexdata"));
		// 索引读取对象
		// 指定读取索引的目录
		IndexReader indexReader = IndexReader.open(d);

		// 索引搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		// 执行搜索
		// 第一个参数：query查询对象，第二个：取出匹配度高的前100条
		TopDocs topDocs = indexSearcher.search(query, 100);

		// 取出匹配上的文档
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			// document的id
			int docID = scoreDoc.doc;

			// 从indexReader根据docID获取document
			Document doc = indexReader.document(docID);

			// 取出doc中的field域的内容
			// 参数指定field域名
			String id = doc.get("id");
			String name = doc.get("name");
			Float price = Float.parseFloat(doc.get("price"));
			String pic = doc.get("pic");

			System.out.println("=====================");
			System.out.println("图书的id：" + id);
			System.out.println("图书的name：" + name);
			System.out.println("图书的价格：" + price);
			System.out.println("图书的图片：" + pic);

		}
		// 关闭资源
		indexReader.close();

	}

	// 搜索索引，直接使用query对象
	@Test
	public void searchIndexByQueryObj() throws ParseException, IOException {

		// 分词,搜索过程使用的分词器要和索引时使用的分词器一致
		Analyzer analyzer = new StandardAnalyzer();

		// TermQuery通过项查询，匹配某个Field
		// 定义term，指定域名及要搜索的关键字
		Term term = new Term("name", "spring");

		TermQuery termQuery = new TermQuery(term);

		// 根据数字范围搜索，根据价格范围搜索
		// 参数：域名、最小值、最大值，是否包括最小值（>=），是否包括最大值（<=）
		NumericRangeQuery<Float> numericRangeQuery = NumericRangeQuery
				.newFloatRange("price", 0f, 60f, true, true);

		// 组合 查询
		// 需求：即根据价格范围搜索，也根据关键字
		BooleanQuery booleanQuery = new BooleanQuery();
		// Occur.MUST 查询条件必须满足，相当于and
		// Occur.SHOULD 查询条件可选，相当于or
		// Occur.MUST_NOT 查询条件不能满足，相当于not非

		booleanQuery.add(termQuery, Occur.MUST);
		booleanQuery.add(numericRangeQuery, Occur.MUST);
		
		

		// 创建索引目录 的流对象,指定索引目录 的位置
		Directory d = FSDirectory.open(new File(
				"F:\\develop\\lucene\\indexdata"));
		// 索引读取对象
		// 指定读取索引的目录
		IndexReader indexReader = IndexReader.open(d);

		// 索引搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		// 执行搜索
		// 第一个参数：query查询对象，第二个：取出匹配度高的前100条
		TopDocs topDocs = indexSearcher.search(booleanQuery, 100);

		// 取出匹配上的文档
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			// document的id
			int docID = scoreDoc.doc;

			// 从indexReader根据docID获取document
			Document doc = indexReader.document(docID);

			// 取出doc中的field域的内容
			// 参数指定field域名
			String id = doc.get("id");
			String name = doc.get("name");
			Float price = Float.parseFloat(doc.get("price"));
			String pic = doc.get("pic");

			System.out.println("=====================");
			System.out.println("图书的id：" + id);
			System.out.println("图书的name：" + name);
			System.out.println("图书的价格：" + price);
			System.out.println("图书的图片：" + pic);
			System.out.println("图书明细：" + doc.get("description"));

		}
		// 关闭资源
		indexReader.close();

	}

	// 搜索索引，使用QueryParser
	@Test
	public void searchIndexByQueryParser() throws ParseException, IOException {

		// 分词,搜索过程使用的分词器要和索引时使用的分词器一致
		Analyzer analyzer = new StandardAnalyzer();

		// 查询分析器
		// 第一个参数：指定默认搜索的域,第二个：分词器
//		QueryParser queryParser = new QueryParser("name", analyzer);

		// 创建查询对象
		//项查询，根据Field搜索，如果这里不指定域名也能查询，是根据queryParser指定的默认查询域来查询
//		Query query = queryParser.parse("description:java");
		
		//实现组织查询
		//根据QueryParser指定的默认域搜索，包括 java或spring的document
//		Query query = queryParser.parse("java spring");
		
		//组合域查询，输入关键字从多个field中匹配
		//设置组合查询域，从name和description两个field域中查询
		String[] fields = {"name","description"};
		//组织域查询的queryParser
		QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
		//输入关键从"name","description"匹配
		Query query  =queryParser.parse("lucene");
		// 创建索引目录 的流对象,指定索引目录 的位置
		Directory d = FSDirectory.open(new File(
				"F:\\develop\\lucene\\indexdata"));
		// 索引读取对象
		// 指定读取索引的目录
		IndexReader indexReader = IndexReader.open(d);

		// 索引搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		// 执行搜索
		// 第一个参数：query查询对象，第二个：取出匹配度高的前100条
		TopDocs topDocs = indexSearcher.search(query, 100);

		// 取出匹配上的文档
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			// document的id
			int docID = scoreDoc.doc;

			// 从indexReader根据docID获取document
			Document doc = indexReader.document(docID);

			// 取出doc中的field域的内容
			// 参数指定field域名
			String id = doc.get("id");
			String name = doc.get("name");
			Float price = Float.parseFloat(doc.get("price"));
			String pic = doc.get("pic");

			System.out.println("=====================");
			System.out.println("图书的id：" + id);
			System.out.println("图书的name：" + name);
			System.out.println("图书的价格：" + price);
			System.out.println("图书的图片：" + pic);

		}
		// 关闭资源
		indexReader.close();

	}

}
