package com.payroom.service;

import java.util.List;

import com.payroom.model.Category;

public interface CategoryService {

	public List<Category> getCategoriesList();

	public Category getCategoryById(int id);

	public void saveCategory(Category category);

	public void deleteCategoryById(int id);

}
