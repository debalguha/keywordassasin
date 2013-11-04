package org.fipecafi.tools.filter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="option")
public class Option {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	@XmlAttribute(name="id")
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@XmlAttribute(name="name")
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Option [id=" + id + ", name=" + name + "]";
	}
}
