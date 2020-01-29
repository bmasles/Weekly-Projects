import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds borrower info
 * 
 * @author Burke Masles
 *
 */
public class Borrower implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7640629773569353516L;
	ArrayList<BookLoan> bookloans = new ArrayList<BookLoan>();
	StringBuilder name = new StringBuilder();
	Borrower(String name) {
		this.name.replace(0, this.name.length(), name);
	}
	public BookLoan getBookloan(int index) {
		return bookloans.get(index);
	}
	public void setBookloan(BookLoan bookloan) {
		bookloans.add(bookloan);
	}
	public StringBuilder getName() {
		return name;
	}
	public void setName(String name) {
		this.name.replace(0, this.name.length(), name);
	}
}
