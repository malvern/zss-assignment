package zw.co.malvern.api.create.category;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.business.create.category.CategoryService;
import zw.co.malvern.utils.exceptions.CategoryException;
import zw.co.malvern.utils.request.CategoryRequest;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorResponse;

@CrossOrigin
@RestController
@RequestMapping("api/book/category")
public class CategoryResource {
    private static Logger LOGGER = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("create")
    @ApiOperation(value = "create category", response = BasicResponse.class)
    public ResponseEntity<BasicResponse> createBookCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.createBookCategory(categoryRequest));
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleCreateException(Exception exception) {
        LOGGER.info("Create book category  exception  -->  ", exception);
        final ErrorResponse error = new ErrorResponse();
        if (exception instanceof CategoryException) {
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
