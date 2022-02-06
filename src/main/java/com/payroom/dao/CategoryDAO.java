package com.payroom.dao;

import java.util.List;

import com.payroom.model.Category;

public interface CategoryDAO {

	public List<Category> findCategoriesList();

	public Category findCategoryById(int id);

	public void saveCategory(Category category);

	public void deleteCategory(int id);

}
