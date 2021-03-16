package zw.co.malvern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.malvern.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitle(String title);
}
