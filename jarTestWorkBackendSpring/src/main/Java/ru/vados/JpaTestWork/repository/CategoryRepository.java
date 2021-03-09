package ru.vados.JpaTestWork.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {

    Category findByName(String categoryName);

    Boolean existsCategoryByName(String categoryName);

    Boolean existsCategoryById(Long categoryId);

    @Query("SELECT c.id FROM Category c where c.reqName =:reqName")
    Long findCategoryIdByReqName(@Param("reqName") String reqName);

    @Query("SELECT c.name FROM Category c")
    List<String> findAllByName();

}
