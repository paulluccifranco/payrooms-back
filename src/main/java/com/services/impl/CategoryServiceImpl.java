package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CategoryDAO;
import com.model.Category;
import com.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoriesDAO;

	@Override
	public List<Category> findCategoriesList() {
		List<Category> listCategories = categoriesDAO.findCategoriesList();
		return listCategories;
	}

	@Override
	public Category findCategoryById(int id) {
		Category category = categoriesDAO.findCategoryById(id);
		return category;
	}

	@Override
	public void saveCategory(Category category) {
		categoriesDAO.saveCategory(category);

	}

	@Override
	public void deleteCategoryById(int id) {
		categoriesDAO.deleteCategory(id);
	}

}
