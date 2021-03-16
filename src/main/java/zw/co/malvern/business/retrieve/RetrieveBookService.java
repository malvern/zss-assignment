package zw.co.malvern.business.retrieve;

import zw.co.malvern.api.retreive.BooksResponse;

public interface RetrieveBookService {
    BooksResponse viewAllAvailableBooks(int page, int size, String sortBy);

    BooksResponse viewByCategory(String category, int page, int size);
}
