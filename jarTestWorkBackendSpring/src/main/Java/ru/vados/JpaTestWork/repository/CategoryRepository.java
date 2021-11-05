package ru.vados.JpaTestWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findAllByDeletedFalse();

    Category findByName(String categoryName);

    Boolean existsCategoryByName(String categoryName);

    Boolean existsCategoryById(Long categoryId);

    @Query("SELECT c.id FROM Category c where c.reqName =:reqName")
    Long findCategoryIdbByReqName(@Param("reqName") String reqName);

    @Query("SELECT c.name FROM Category c")
    List<String> getAllCategoryNames();

}
