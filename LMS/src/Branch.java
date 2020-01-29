import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 
 */

/**
 * Holds all info about LMS branch
 * 
 * @author Burke Masles
 *
 */
public class Branch implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7939492017988728618L;
	public ArrayList<String> genres = new ArrayList<String>();
	public ArrayList<Book> books = new ArrayList<Book>();
	public ArrayList<String> publishers = new ArrayList<String>();
	public ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
	public String name;;

	public Book getBook(int index) {
		return books.get(index);
	}

	public void setBook(Book bk) {
		books.add(bk);
	}

	public String getGenre(int index) {
		return genres.get(index);
	}

	public void setGenre(String str) {
		genres.add(str);
	}

	public String getPublisher(int index) {
		return genres.get(index);
	}
	
	public void setPublisher(String str) {
		publishers.add(str);
	}
	
	public Borrower getBorrower(int index) {
		return borrowers.get(index);
	}
	
	public void setBorrower(Borrower br) {
		borrowers.add(br);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Branch brch = new Branch();
		brch.setBook(new Book(5, "Bible", "Church"));
		brch.setBook(new Book(10, "To Kill a Mockingbird", "Penguin"));
		brch.setBorrower(new Borrower("Josh"));
		brch.getBorrower(0).setBookloan(new BookLoan(0, "Bible", "Church"));
		brch.setGenre("Biography");
		brch.setGenre("Non-Fiction");
		brch.setPublisher("Penguin");
		brch.setPublisher("Church");
		try (FileOutputStream file = new FileOutputStream("test.ser");
				ObjectOutputStream out = new ObjectOutputStream(file)) {
			out.writeObject(brch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (FileInputStream file = new FileInputStream("test.ser");
				ObjectInputStream in = new ObjectInputStream(file)) {
			Branch brch2 = (Branch) in.readObject();
			System.out.println(brch2.getBook(0).getName());
			System.out.println(brch2.getBorrower(0).getBookloan(0).dueDate);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
