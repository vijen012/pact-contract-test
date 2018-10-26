package com.user.data;

import org.springframework.stereotype.Component;

@Component
public class UserResponseData {
	private String title;
	private String name;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "UserResponseData [title=" + title + ", name=" + name + "]";
	}
}
