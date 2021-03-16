package zw.co.malvern.api.book.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.business.book.BookService;
import zw.co.malvern.utils.exceptions.BookException;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorResponse;

@CrossOrigin
@RestController
@RequestMapping("api/book")
public class BookResource {

    private static Logger LOGGER = LoggerFactory.getLogger(BookResource.class);
    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("create")
    public ResponseEntity<BasicResponse> createNewBook(@RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.createNewBook(bookRequest));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleCreateException(Exception exception) {
        LOGGER.info("Create request throw exception  -->  ", exception);
        final ErrorResponse error = new ErrorResponse();
        if (exception instanceof BookException) {
            error.setError("Error due to user request");
            error.setNarrative(exception.getMessage());
            error.setSuccess(false);
            return ResponseEntity.badRequest().body(error);
        } else {
            error.setError("Internal Error Occurred");
            error.setNarrative("Error with error code XXXIX occurred.See backend manual for reference");
            error.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

        }
    }

}
