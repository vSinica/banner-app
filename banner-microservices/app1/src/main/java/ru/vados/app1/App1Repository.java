package ru.vados.app1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface App1Repository extends JpaRepository<App1Entity, Long> {
}
