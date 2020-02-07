package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Branch;

public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO(Connection conn) {
		super(conn);
	}
	
	public void addBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("insert into tbl_library_branch (branchName,branchAddress) values (?,?,?)", 
				new Object[] {branch.getBranchName(),branch.getBranchAddress()});
	}

	public void updateBranch(Branch branch) throws SQLException, ClassNotFoundException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?", 
				new Object[]{branch.getBranchName(),branch.getBranchAddress()
						,branch.getBranchId()} );
	}

	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("delete from tbl_library_branch where branchID = ?", new Object[] {branch.getBranchId()});
	}
	
	public List<Branch> readBranch() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch", null);
	}

	@Override
	List<Branch> extractData(ResultSet rs) throws SQLException {
		List<Branch> branches = new ArrayList<Branch>();
		while (rs.next()) {
			Branch br = new Branch();
			br.setBranchId(rs.getInt("branchId"));
			br.setBranchName(rs.getString("branchName"));
			br.setBranchAddress(rs.getString("branchAddress"));
			branches.add(br);
		}
		return branches;
	}

}
