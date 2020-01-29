import java.io.Serializable;

/**
 * 
 */

/**
 * Contains book info including count in branch
 * 
 * @author Burke Masles
 *
 */
public class Book implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1219793077687069428L;
	int count;
	String name, author;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	Book(int count, String name, String author) {
		this.count = count;
		this.name = name;
		this.author = author;
	}
}
