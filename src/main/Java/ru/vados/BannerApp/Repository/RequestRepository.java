package ru.vados.BannerApp.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vados.BannerApp.Entity.RequestEntity;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity,Long> {

    @Query("SELECT r FROM RequestEntity r WHERE r.datetime  > (current_date() - 1)")
    List<RequestEntity> findLastDayRequest();

    RequestEntity findByUserAgent(String userAgent);
}
