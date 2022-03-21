package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.CategoryDAO;
import com.payroom.model.Category;
import com.payroom.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoriesDAO;

	@Override
	public List<Category> getCategoriesList() {
		List<Category> listCategories = categoriesDAO.getCategoriesList();
		return listCategories;
	}

	@Override
	public Category getCategoryById(int id) {
		Category category = categoriesDAO.getCategoryById(id);
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
