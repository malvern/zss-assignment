package zw.co.malvern.business.create.category;

import zw.co.malvern.domain.Category;
import zw.co.malvern.repository.CategoryRepository;
import zw.co.malvern.utils.exceptions.CategoryException;
import zw.co.malvern.utils.request.CategoryRequest;
import zw.co.malvern.utils.response.BasicResponse;

import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BasicResponse createBookCategory(CategoryRequest categoryRequest) {
        validateCategoryRequest(categoryRequest);
        checkIfCategoryExist(categoryRequest);
        final Category savedCategory = categoryRepository.save(convertBookCategory(categoryRequest));
        return buildResponse(savedCategory);
    }

    private void validateCategoryRequest(CategoryRequest categoryRequest) {
        if (categoryRequest.getTitle() == null || categoryRequest.getTitle().isEmpty())
            throw new CategoryException("Category title cannot be empty");
    }

    private void checkIfCategoryExist(CategoryRequest categoryRequest) {
        final Optional<Category> existingCategory = categoryRepository
                .findByTitleIgnoreCase(categoryRequest.getTitle());
        if (existingCategory.isPresent())
            throw new CategoryException("Given book category already exist");
    }

    private BasicResponse buildResponse(Category savedCategory) {
        final BasicResponse response = new BasicResponse();
        response.setSuccess(true);
        response.setNarrative(savedCategory.getTitle() + " category successfully created");
        return response;
    }

    private Category convertBookCategory(CategoryRequest categoryRequest) {
        final Category category = new Category();
        category.setTitle(categoryRequest.getTitle());
        return category;
    }
}
