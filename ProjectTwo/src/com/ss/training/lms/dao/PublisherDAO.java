package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);
	}
	
	public void addPublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		save("insert into tbl__publisher (pubisherName,publisherAddress,publisherPhone) values (?,?,?)", 
				new Object[] {pub.getPublisherName(),pub.getPublisherAddress(),pub.getPublisherPhone()});
	}

	public void updatePublisher(Publisher pub) throws SQLException, ClassNotFoundException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?", 
				new Object[]{pub.getPublisherName(),pub.getPublisherAddress(),pub.getPublisherPhone(),pub.getPublisherId()} );
	}

	public void deletePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] {pub.getPublisherId()});
	}
	
	public List<Publisher> readPublisher() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher", null);
	}

	@Override
	List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> pubs = new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			pub.setPublisherPhone(rs.getString("publisherPhone"));
			pubs.add(pub);
		}
		return pubs;
	}

}
