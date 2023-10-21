package net.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.skhu.dto.Document;
import net.skhu.dto.Folder;
import net.skhu.model.DocumentEdit;
import net.skhu.model.Pagination;
import net.skhu.service.DocumentService;
import net.skhu.service.FolderService;

@Slf4j
@Controller
@RequestMapping("document")
public class DocumentController {

    @Autowired DocumentService documentService;
    @Autowired FolderService folderService;

    @GetMapping("list")
    public String list(Model model, Pagination pagination) {
        List<Document> documents = documentService.findAll(pagination);
        model.addAttribute("documents", documents);
        model.addAttribute("orders", documentService.getOrders());
        return "document/list";
    }

    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
    	DocumentEdit documentEdit = new DocumentEdit();
        List<Folder> folders = folderService.findAll();
        model.addAttribute("documentEdit", documentEdit);
        model.addAttribute("folders", folders);
        return "document/edit";
    }

    @PostMapping("create")
    public String create(Model model, Pagination pagination,
            @Valid DocumentEdit documentEdit, BindingResult bindingResult) {
        try {
            documentService.insert(documentEdit, bindingResult, pagination);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception exception) {
            model.addAttribute("folders", folderService.findAll());
            bindingResult.rejectValue("", null, "등록할 수 없습니다.");
            log.error("등록 실패 {}", documentEdit, exception);
            return "document/edit";
        }
    }

    @GetMapping("edit")
    public String edit(Model model, int id, Pagination pagination) {
    	DocumentEdit documentEdit = documentService.findOne(id);
        List<Folder> folders = folderService.findAll();
        model.addAttribute("documentEdit", documentEdit);
        model.addAttribute("folders", folders);
        return "document/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Pagination pagination,
            @Valid DocumentEdit documentEdit, BindingResult bindingResult) {
        try {
            documentService.update(documentEdit, bindingResult);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception exception) {
            model.addAttribute("folders", folderService.findAll());
            bindingResult.rejectValue("", null, "수정할 수 없습니다.");
            log.error("수정 실패 {}", documentEdit, exception);
            return "document/edit";
        }
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, Pagination pagination,
    		DocumentEdit documentEdit, BindingResult bindingResult) {
        try {
            documentService.delete(documentEdit.getId());
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception exception) {
            model.addAttribute("folders", folderService.findAll());
            bindingResult.rejectValue("", null, "삭제할 수 없습니다.");
            log.error("삭제 실패 {}", documentEdit, exception);
            return "document/edit";
        }
    }
}

