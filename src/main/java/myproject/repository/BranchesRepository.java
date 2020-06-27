package myproject.repository;

import myproject.model.Branches;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author paveldikin
 * @date 27.06.2020
 */
@Repository
public interface MyCrudRepository extends CrudRepository<Branches,Long>{
    Branches findBranchesById(Long id);
    List<Branches> findAll();
}
