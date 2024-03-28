package ru.vados.BannerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vados.BannerApp.entity.BannerEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity,Long> {

    List<BannerEntity> findBannerByDeletedIsFalse();

    Optional<BannerEntity> findByIdAndDeletedIsFalse(Long id);

    Optional<BannerEntity> findByName(String name);
}
