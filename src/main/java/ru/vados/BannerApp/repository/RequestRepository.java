package ru.vados.BannerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vados.BannerApp.entity.RequestEntity;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity,Long> {

    @Query("SELECT r FROM RequestEntity r WHERE r.datetime  > (current_date() - 1)")
    List<RequestEntity> findLastDayRequest();

    RequestEntity findByUserAgent(String userAgent);
}
