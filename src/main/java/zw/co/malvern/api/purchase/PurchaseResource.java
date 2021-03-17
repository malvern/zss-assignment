package zw.co.malvern.api.purchase;


import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.business.purchase.PurchaseService;
import zw.co.malvern.utils.exceptions.DateTimeException;
import zw.co.malvern.utils.response.ErrorResponse;

@CrossOrigin
@RestController
@RequestMapping("api/purchase")
public class PurchaseResource {
    private static Logger LOGGER = LoggerFactory.getLogger(PurchaseResource.class);

    private final PurchaseService purchaseService;

    public PurchaseResource(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("book")
    @ApiOperation(value = "Purchase a book", response = PurchaseResponse.class)
    public ResponseEntity<PurchaseResponse> purchaseBook(@RequestBody PurchaseRequest purchaseRequest) {
        return ResponseEntity.ok(purchaseService.purchaseBook(purchaseRequest));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleCreateException(Exception exception) {
        LOGGER.info("Create book category  exception  -->  ", exception);
        final ErrorResponse error = new ErrorResponse();
        if (exception instanceof DateTimeException) {
            error.setError("Error due to user request");
            error.setNarrative(exception.getMessage());
            error.setSuccess(false);
            return ResponseEntity.badRequest().body(error);
        } else {
            error.setError("Internal Error Occurred");
            error.setNarrative(exception.getMessage());
            error.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

        }
    }

}
