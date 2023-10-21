package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import net.skhu.dto.Document;
import net.skhu.mapper.DocumentMapper;
import net.skhu.mapper.DocumentMapper.Order;
import net.skhu.model.DocumentEdit;
import net.skhu.model.Pagination;

@Service
public class DocumentService {

    @Autowired DocumentMapper documentMapper;
    ModelMapper modelMapper = new ModelMapper();

    public Order[] getOrders() {
        return DocumentMapper.orders;
    }

    public DocumentEdit findOne(int id) {
        Document documentDto = documentMapper.findOne(id);
        return toEditModel(documentDto);
    }

    public Document findByFileName(String fileName) {
        return documentMapper.findByFileName(fileName);
    }

    public List<Document> findAll(Pagination pagination) {
        pagination.setRecordCount(documentMapper.getCount(pagination));
        return documentMapper.findAll(pagination);
    }

    public void insert(DocumentEdit documentEdit, BindingResult bindingResult,
            Pagination pagination) throws Exception {
        if (hasErrors(documentEdit, bindingResult))
            throw new Exception("입력 데이터 오류");
        Document document = toDto(documentEdit);
        documentMapper.insert(document);
        pagination.setSt("");
        int lastPage = (int)Math.ceil((double)documentMapper.getCount(pagination) / pagination.getSz());
        pagination.setPg(lastPage);
    }

    public void update(DocumentEdit documentEdit,
            BindingResult bindingResult) throws Exception {
        if (hasErrors(documentEdit, bindingResult))
            throw new Exception("입력 데이터 오류");
        Document document = toDto(documentEdit);
        documentMapper.update(document);
    }

    public void delete(int id) {
        documentMapper.delete(id);
    }

    public Document toDto(DocumentEdit documentEdit) {
        return modelMapper.map(documentEdit, Document.class);
    }

    public DocumentEdit toEditModel(Document documentDto) {
        return modelMapper.map(documentDto, DocumentEdit.class);
    }

    public boolean hasErrors(DocumentEdit documentEdit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return true;
        Document document2 = findByFileName(documentEdit.getFileName());
        if (document2 != null && document2.getId() != documentEdit.getId()) {
            bindingResult.rejectValue("fileName", null, "파일명이 중복됩니다.");
            return true;
        }
        return false;
    }
}

