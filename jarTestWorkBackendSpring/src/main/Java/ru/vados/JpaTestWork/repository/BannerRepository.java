package ru.vados.JpaTestWork.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.model.Banner;

import java.util.List;

@Repository
public interface BannerRepository extends CrudRepository<Banner,Long> {
    Banner findByName(String name);

    @Query(value = "SELECT DISTINCT * FROM banners b WHERE b.category_id=?1", nativeQuery=true)
    List<Banner> findBannersByCategoryId(Long category_id);

}
