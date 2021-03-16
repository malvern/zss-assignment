package zw.co.malvern.api.retreive;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.business.retrieve.RetrieveBookService;

@CrossOrigin
@RestController
@RequestMapping("api/book/view")
public class RetrieveBookResource {

    private final RetrieveBookService retrieveBookService;

    public RetrieveBookResource(RetrieveBookService retrieveBookService) {
        this.retrieveBookService = retrieveBookService;
    }

    @GetMapping(value = "all", params = {"page", "size"})
    @ApiOperation(value = "view all books", response = BooksResponse.class)

    public ResponseEntity<BooksResponse> viewAllAvailableBooks(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "sortBy", defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok().body(retrieveBookService.viewAllAvailableBooks(page, size, sortBy));

    }

    @GetMapping(value = "category/{category}", params = {"page", "size"})
    @ApiOperation(value = "view books by category", response = BooksResponse.class)
    public ResponseEntity<BooksResponse> viewBooksByCategory(@PathVariable String category,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(retrieveBookService.viewByCategory(category, page, size));
    }

}
