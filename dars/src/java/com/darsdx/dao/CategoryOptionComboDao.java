/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.CategoryOptionCombo;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CategoryOptionComboDao 
{
    public void saveCategoryOptionCombination(CategoryOptionCombo cc) throws Exception;
    public void updateCategoryOptionCombination(CategoryOptionCombo cc) throws Exception;
    public void deleteCategoryOptionCombination(CategoryOptionCombo cc) throws Exception;
    public CategoryOptionCombo getCategoryOptionCombination(String categoryOptionComboId) throws Exception;
    public CategoryOptionCombo getCategoryOptionComboByName(String categoryOptionComboName) throws Exception;
    public List getAllCategoryOptionCombinations() throws Exception;
    public CategoryOptionCombo getCategoryOptionCombination(String dhisId,String categoryOptionComboId) throws Exception;
    public List getCategoryOptionCombinationsByDhisId(String dhisId) throws Exception;
}
