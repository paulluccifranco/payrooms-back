package com.payroom.dao;

import java.util.List;

import com.payroom.model.Category;

public interface CategoryDAO {

	public List<Category> getCategoriesList();

	public Category getCategoryById(int id);

	public void saveCategory(Category category);

	public void deleteCategory(int id);

}
