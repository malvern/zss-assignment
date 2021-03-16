package zw.co.malvern.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import zw.co.malvern.api.create.category.CategoryResource;
import zw.co.malvern.business.category.CategoryService;
import zw.co.malvern.utils.exceptions.CategoryException;
import zw.co.malvern.utils.request.CategoryRequest;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zw.co.malvern.utils.TestData.categoryRequest;

@WebMvcTest(controllers = {CategoryResource.class})
@ActiveProfiles("dev")
class CategoryResourceControllerUnitTest {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("creating book category")
    void createNewBookCategoryShouldReturnSuccess() throws Exception {
        final String url = "/api/book/category/create";
        final BasicResponse basicResponse = new BasicResponse();
        basicResponse.setNarrative("educational category successfully created");
        basicResponse.setSuccess(true);
        given(categoryService.createBookCategory(any(CategoryRequest.class))).willReturn(basicResponse);
        mockMvc.perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(categoryRequest())))
                .andExpect(status().is(200))
                .andExpect(jsonPath("narrative").value(basicResponse.getNarrative()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(basicResponse.isSuccess()))
                .andReturn();
        verify(categoryService, times(1)).createBookCategory(any(CategoryRequest.class));
    }

    @Test
    @DisplayName("with invalid request return 404")
    void givenInvalidBookCategoryRequest_whenCreatingNewRequest_shouldReturn400Error() throws Exception {
        final String url = "/api/book/category/create";
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setNarrative("Given book category already exist");
        errorResponse.setSuccess(false);
        errorResponse.setError("Error due to use request");
        given(categoryService.createBookCategory(any(CategoryRequest.class)))
                .willThrow(new CategoryException("Given book category already exist"));
        mockMvc.perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(categoryRequest())))
                .andExpect(status().is(400))
                .andExpect(jsonPath("narrative").value(errorResponse.getNarrative()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(errorResponse.isSuccess()))
                .andReturn();
        verify(categoryService, times(1)).createBookCategory(any(CategoryRequest.class));
    }
}
