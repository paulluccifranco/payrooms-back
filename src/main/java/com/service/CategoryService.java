package com.service;

import java.util.List;

import com.model.Category;

public interface CategoryService {

	public List<Category> findCategoriesList();

	public Category findCategoryById(int id);

	public void saveCategory(Category category);

	public void deleteCategoryById(int id);

}
