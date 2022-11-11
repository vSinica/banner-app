package ru.vados.JpaTestWork.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.Entity.RequestEntity;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity,Long> {

    @Query(value="SELECT * FROM requests WHERE datetime >(NOW()-INTERVAL '1 DAY')",nativeQuery = true)
    List<RequestEntity> findLastDayRequest();
}
