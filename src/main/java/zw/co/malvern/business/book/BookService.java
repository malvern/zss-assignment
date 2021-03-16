package zw.co.malvern.business.book;

import zw.co.malvern.api.create.book.BookRequest;
import zw.co.malvern.utils.response.BasicResponse;

@FunctionalInterface
public interface BookService {

    BasicResponse createNewBook(BookRequest bookRequest);
}
