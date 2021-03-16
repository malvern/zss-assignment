package zw.co.malvern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.co.malvern.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitleIgnoreCase(String title);

    @Query(nativeQuery = true, value = "SELECT title from category where id =:id")
    String findCategoryById(@Param("id") Long id);
}
