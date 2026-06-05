
package com.product.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    RepoCategory repo;

    @Override
    public List<Category> findAll() {
        try {
            return repo.findAll();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findActive() {
        try {
            return repo.findByStatusOrderByCategoryAsc(1);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void create(DtoCategoryIn in) {
        try {
            Category category = new Category();
            category.setCategory(in.getCategory());
            category.setTag(in.getTag());
            category.setStatus(1); 

            repo.save(category);

        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");

            if (e.getLocalizedMessage().contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");

            throw new DBAccessException(e);
        }
    }

    @Override
    public void update(DtoCategoryIn in, Integer id) {
        try {
            Optional<Category> categorySaved = repo.findById(id);

            if (categorySaved.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
            }

            Category category = categorySaved.get();
            category.setCategory(in.getCategory());
            category.setTag(in.getTag());

            repo.save(category);

        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");

            if (e.getLocalizedMessage().contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");

            throw new DBAccessException(e);
        }
    }

    @Override
    public void enable(Integer id) {
        validateId(id);
        repo.updateStatus(id, 1);
    }

    @Override
    public void disable(Integer id) {
        validateId(id);
        repo.updateStatus(id, 0);
    }

    private void validateId(Integer id) {
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
    }
}

