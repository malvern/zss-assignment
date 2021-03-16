package zw.co.malvern.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import zw.co.malvern.business.create.category.CategoryService;
import zw.co.malvern.business.create.category.CategoryServiceImpl;
import zw.co.malvern.domain.Category;
import zw.co.malvern.repository.CategoryRepository;
import zw.co.malvern.utils.exceptions.CategoryException;
import zw.co.malvern.utils.request.CategoryRequest;
import zw.co.malvern.utils.response.BasicResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static zw.co.malvern.utils.TestData.bookCategory;
import static zw.co.malvern.utils.TestData.categoryRequest;

class CategoryServiceUnitTest {
    @Mock
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    @DisplayName("creating new category")
    void givenCategoryRequest_whenCreatingNewCategory_shouldReturnSuccess() {
        given(categoryRepository.findByTitleIgnoreCase(anyString())).willReturn(Optional.empty());
        given(categoryRepository.save(any(Category.class))).willReturn(bookCategory());
        final BasicResponse response = categoryService.createBookCategory(categoryRequest());
        assertThat(response).as("category response").isNotNull();
        assertThat(response.isSuccess()).as("category status").isEqualTo(true);
        assertThat(response.getNarrative()).as("category narrative")
                .isEqualTo("educational category successfully created");
        verify(categoryRepository, times(1)).findByTitleIgnoreCase(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("attempt to create existing category")
    void givenExistingCategoryTitle_whenCreatingNewCategory_shouldThrowException() {
        given(categoryRepository.findByTitleIgnoreCase(anyString())).willReturn(Optional.of(bookCategory()));
        assertThatThrownBy(() -> categoryService.createBookCategory(categoryRequest()))
                .hasMessage("Given book category already exist")
                .isInstanceOf(CategoryException.class);
        verify(categoryRepository, times(1)).findByTitleIgnoreCase(anyString());

    }

    @Test
    @DisplayName("attempt to create new category with invalid request")
    void givenInvalidCategoryRequest_whenCreatingNewCategory_shouldThrowException() {
        final CategoryRequest categoryRequest = categoryRequest();
        categoryRequest.setTitle(null);
        assertThatThrownBy(() -> categoryService.createBookCategory(categoryRequest))
                .hasMessage("Category title cannot be empty")
                .isInstanceOf(CategoryException.class);

    }
}
