package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;




    //private List<Category> categories=new ArrayList<>();
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) throws  ResponseStatusException{
       // List<Category> categories=categoryRepository.findAll();

        Category category =categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        categoryRepository.delete(category);
        return "Category with categoryId: "+ categoryId + " Deleted Successfully";
    }




    @Override
    public Category updateCategory(Category category, Long categoryID) throws ResponseStatusException {
       // List<Category> categories=categoryRepository.findAll();

        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryID);

        Category savedCategory=savedCategoryOptional
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not found"));


        savedCategory.setCategoryName(category.getCategoryName());
        savedCategory=categoryRepository.save(savedCategory);
        return  savedCategory;
    }
}
