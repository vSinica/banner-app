package ru.vados.JpaTestWork.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.Entity.Banner;
import ru.vados.JpaTestWork.Entity.Category;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {

    List<Banner> findBannerByDeletedIsFalse();

}
