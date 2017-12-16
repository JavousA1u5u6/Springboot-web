package cn.itcast.lucene.po;

/**
 * 
 * <p>Title: Book</p>
 * <p>Description: 图书信息实体类</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智播客
 * @date	2020-12-12
 * @version 1.0
 */
public class Book {

	//属性和数据库中表的字段一一对应
	//图书表的主键
	private Integer id;
	//图书的名称
	private String name;
	//图书的价格
	private Float price;
	//图书的图片
	private String pic;
	//图书的描述
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
