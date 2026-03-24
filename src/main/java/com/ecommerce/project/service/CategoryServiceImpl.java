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
        List<Category> categories=categoryRepository.findAll();

        Category category=categories.stream()
                .filter(c->c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));
        categoryRepository.delete(category);
        return "Category with categoryId: "+ categoryId + " Deleted Successfully";
    }




    @Override
    public Category updateCategory(Category category, Long categoryID) throws ResponseStatusException {
        List<Category> categories=categoryRepository.findAll();
       Optional<Category> optionalCategory= categories.stream()
                .filter(c->c.getCategoryId().equals(categoryID))
               .findFirst();

       if(optionalCategory.isPresent()){
           Category existingCategory=optionalCategory.get();
           existingCategory.setCategoryName(category.getCategoryName());
           Category savedCategory=categoryRepository.save(existingCategory);
           return savedCategory;
       }else{
           throw   new  ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not found");
       }

    }
}
