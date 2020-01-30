/**
 * 
 */
package com.SmoothStack.WeeklyProjects.ProjectOne;

/**
 * Basic information and methods all entities (book, publisher, author) need to
 * have
 * 
 * @author Burke Masles
 *
 */
public interface Entity {
	int getId();
	void setId(int id);
	String getName();
	void setName(String name);
}
