package zw.co.malvern.api.retreive;

import zw.co.malvern.utils.response.BasicResponse;

import java.util.List;

public class BooksResponse extends BasicResponse {
    private List<BookDto> bookDto;

    public List<BookDto> getBookDto() {
        return bookDto;
    }

    public void setBookDto(List<BookDto> bookDto) {
        this.bookDto = bookDto;
    }
}
