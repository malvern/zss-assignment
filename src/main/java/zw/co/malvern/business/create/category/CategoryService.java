package zw.co.malvern.business.create.category;

import zw.co.malvern.utils.request.CategoryRequest;
import zw.co.malvern.utils.response.BasicResponse;

@FunctionalInterface
public interface CategoryService {

    BasicResponse createBookCategory(CategoryRequest categoryRequest);
}
