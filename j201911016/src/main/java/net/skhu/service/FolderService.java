package net.skhu.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.dto.Folder;
import net.skhu.mapper.FolderMapper;
@Service
public class FolderService {
 @Autowired
 public FolderMapper folderMapper;
 public List<Folder> findAll() {
 return folderMapper.findAll();
 }
}