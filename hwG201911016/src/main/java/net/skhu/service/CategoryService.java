package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.entity.Category;
import net.skhu.repository.CategoryRepository;


@Service
public class CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}

