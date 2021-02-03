package ru.vados.JpaTestWork.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vados.JpaTestWork.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request,Long> {

    @Query(value="SELECT * FROM requests WHERE datetime >(NOW()-INTERVAL 1 DAY)",nativeQuery = true)
    List<Request> findLastDayRequest();
}
