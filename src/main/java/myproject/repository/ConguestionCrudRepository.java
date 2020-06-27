package myproject.repository;

import myproject.model.Сongestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author paveldikin
 * @date 27.06.2020
 */
@Repository
public interface ConguestionCrudRepository extends CrudRepository<Сongestion, Long> {
    List<Сongestion> findAll();
}
