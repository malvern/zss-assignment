package zw.co.malvern.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.co.malvern.domain.Book;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(nativeQuery = true, value = "SELECT * from book JOIN category on book.category_id = category.id")
    List<Book> findAllAvailableBooks();

    @Query(nativeQuery = true,
            value = "SELECT * from book JOIN category on book.category_id = category.id WHERE category.title =:title")
    Page<Book> findBooksByCategory(@Param("title") String title, Pageable pageable);



}
