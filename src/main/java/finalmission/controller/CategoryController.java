package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.common.Role;
import finalmission.dto.request.CategoryRequest;
import finalmission.dto.response.CategoryResponse;
import finalmission.service.CategoryService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @CheckRole(Role.ADMIN)
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest category) {
        CategoryResponse categoryResponse = categoryService.createCategory(category);
        return ResponseEntity.created(URI.create("/categories/" + categoryResponse.id())).build();
    }

}
