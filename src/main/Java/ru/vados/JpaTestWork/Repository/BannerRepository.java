package ru.vados.JpaTestWork.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.Entity.BannerEntity;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity,Long> {

    List<BannerEntity> findBannerByDeletedIsFalse();

}
