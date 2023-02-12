package ru.vados.BannerApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vados.BannerApp.Entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    List<CategoryEntity> findAllByDeletedFalse();

    Optional<CategoryEntity> findByName(String categoryName);

    Boolean existsCategoryEntityByName(String categoryName);

    Boolean existsCategoryEntityById(Long categoryId);

    Boolean existsCategoryEntityByReqName(String categoryReqName);

    @Query("SELECT c.id FROM CategoryEntity c where c.reqName =:reqName")
    Long findCategoryIdbByReqName(@Param("reqName") String reqName);

    Optional<CategoryEntity> findCategoryByReqName(String reqName);

    @Query("SELECT c.name FROM CategoryEntity c")
    List<String> getAllCategoryNames();

}
