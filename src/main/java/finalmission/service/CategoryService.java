package finalmission.service;

import finalmission.dto.request.CategoryRequest;
import finalmission.dto.response.CategoryResponse;
import finalmission.entity.Category;
import finalmission.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequest.toCategory();
        return CategoryResponse.from(categoryRepository.save(category));
    }
}
