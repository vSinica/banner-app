package ru.vados.JpaTestWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {

    List<Banner> findByCategoryId(Category category);

}
