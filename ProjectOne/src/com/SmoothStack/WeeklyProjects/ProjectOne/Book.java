/**
 * 
 */
package com.SmoothStack.WeeklyProjects.ProjectOne;

/**
 * @author Burke Masles
 *
 */
public class Book implements Entity {
	int id, authorId, publisherId;
	String name;

	Book() {
		id = -1;
		name = "Error";
		publisherId = -1;
		authorId = -1;
	}

	Book(int id, String name, int publisherId, int authorId) {
		this.id = id;
		this.name = name;
		this.publisherId = publisherId;
		this.authorId = authorId;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorId;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + publisherId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (authorId != other.authorId)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (publisherId != other.publisherId)
			return false;
		return true;
	}

	
	

}
