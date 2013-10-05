package com.debal.java.test;

public class SLinkedList {
	protected Node head;
	// head node of the list
	protected long size; // number of nodes in the list
	
	protected Node last;

	/** Default constructor that creates an empty list */
	public SLinkedList() {
		head = null;
		size = 0;
		last = null;
	}
	
	public void addElement(String element){
		Node node = new Node(element, null);
		if(head==null)
			head = last = node;
		else if(last!=null){
			last.setNext(node);
			last = node;
		}else{
			head.setNext(node);
			last = node;
		}
	}
}
