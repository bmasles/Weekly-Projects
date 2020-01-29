import java.io.Serializable;

/**
 * 
 */

/**
 * @author Burke
 *
 */
public class BookLoan extends Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4281107568746501870L;
	// TODO replace these with actual dates when we learn about them
	String currentDate = "1/29/2020";
	String dueDate = "2/5/2020";

	BookLoan(int count, String name, String author) {
		super(count, name, author);
	}

}
