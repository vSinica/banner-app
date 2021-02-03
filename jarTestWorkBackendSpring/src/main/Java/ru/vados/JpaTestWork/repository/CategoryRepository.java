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

    @Query(value = "SELECT c.category_id FROM category c where c.req_name =?1", nativeQuery = true)
    Long findCategoryIdByReqName(String req_name);

}
