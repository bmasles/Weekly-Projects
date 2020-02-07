package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Copies;

public class CopiesDAO extends BaseDAO<Copies> {

	public CopiesDAO(Connection conn) {
		super(conn);
	}
	
	public void addCopies(Copies copies) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_copies (bookId,branchId,noOfCopies) values (?,?,?)", 
				new Object[] {copies.getBookId(),copies.getBranchId(),copies.getNoOfCopies()});
	}

	public void updateCopies(Copies copies) throws SQLException, ClassNotFoundException {
		save("update tbl_book_copies set noOfcopies where bookId = ? and branchId = ?", 
				new Object[]{copies.getNoOfCopies(),copies.getBookId(),copies.getBranchId()} );
	}

	public void deleteCopies(Copies copies) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_copies where bookId = ? and branchId = ?"
				,new Object[] {copies.getBookId(),copies.getBranchId()});
	}
	
	public List<Copies> readCopies() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies", null);
	}

	@Override
	List<Copies> extractData(ResultSet rs) throws SQLException {
		List<Copies> copies = new ArrayList<Copies>();
		while (rs.next()) {
			Copies copy = new Copies();
			copy.setBookId(rs.getInt("bookId"));
			copy.setBranchId(rs.getInt("branchId"));
			copy.setNoOfCopies(rs.getInt("noOfCopies"));
			copies.add(copy);
		}
		return copies;
	}

}
