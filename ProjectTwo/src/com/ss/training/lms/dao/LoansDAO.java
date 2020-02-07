package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Loans;

public class LoansDAO extends BaseDAO<Loans> {

	public LoansDAO(Connection conn) {
		super(conn);
	}
	
	public void addLoans(Loans loan) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_loans (bookId,branchId,cardNo,dateIn,dueDate,dateOut) values (?,?,?,?,?,?)", 
				new Object[] {loan.getBookId(),loan.getBranchId(),loan.getCardNo(),loan.getDateIn(),
						loan.getDueDate(),loan.getDateOut()});
	}

	public void updateLoans(Loans loan) throws SQLException, ClassNotFoundException {
		save("update tbl_book_loans set dateIn = ?, dueDate = ?, dateOut = ? where bookId = ? and branchId = ? and cardNo = ?", 
				new Object[]{loan.getDateIn(),loan.getDueDate(),loan.getDateOut(),
						loan.getBookId(),loan.getBranchId(),loan.getCardNo()} );
	}

	public void deleteLoans(Loans loan) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_loans where bookId = ?, branchId = ?, cardNo = ?",
				new Object[] {loan.getBookId(),loan.getBranchId(),loan.getCardNo()});
	}
	
	public List<Loans> readLoans() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans", null);
	}

	@Override
	List<Loans> extractData(ResultSet rs) throws SQLException {
		List<Loans> loans = new ArrayList<Loans>();
		while (rs.next()) {
			Loans loan = new Loans();
			loan.setBookId(rs.getInt("bookId"));
			loan.setBranchId(rs.getInt("branchId"));
			loan.setCardNo(rs.getInt("cardNo"));
			loan.setDateIn(rs.getDate("dateIn"));
			loan.setDueDate(rs.getDate("dueDate"));
			loan.setDateOut(rs.getDate("dateOut"));
			loans.add(loan);
		}
		return loans;
	}

}
