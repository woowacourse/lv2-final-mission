package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.dto.request.CategoryRequest;
import finalmission.dto.response.CategoryResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void 카테고리_추가() {

        // given
        String name = "이름";
        String description = "설명";
        CategoryRequest categoryRequest = new CategoryRequest(name, description);

        // when
        CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);

        // then
        assertThat(savedCategory.name()).isEqualTo(name);
        assertThat(savedCategory.description()).isEqualTo(description);
    }

    @Test
    void 전체_카테고리_조회() {
        // given & when
        List<CategoryResponse> allCategories = categoryService.getAllCategories();

        // then
        assertThat(allCategories).hasSize(1);
    }
}
