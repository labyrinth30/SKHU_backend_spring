package net.skhu.dto;

import lombok.Data;

@Data
public class Document {
	int id;
	String fileName;
	int size;
	int folderId;
	String state;
	String modifiedDate;
	
	String title;
}