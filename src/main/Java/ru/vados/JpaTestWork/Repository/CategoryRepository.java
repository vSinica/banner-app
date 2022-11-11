package ru.vados.JpaTestWork.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.Entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    List<CategoryEntity> findAllByDeletedFalse();

    Optional<CategoryEntity> findByName(String categoryName);

    Boolean existsCategoryByName(String categoryName);

    Boolean existsCategoryById(Long categoryId);

    @Query("SELECT c.id FROM CategoryEntity c where c.reqName =:reqName")
    Long findCategoryIdbByReqName(@Param("reqName") String reqName);

    Optional<CategoryEntity> findCategoryByReqName(String reqName);

    @Query("SELECT c.name FROM CategoryEntity c")
    List<String> getAllCategoryNames();

}
