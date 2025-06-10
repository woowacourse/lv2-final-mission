package finalmission.dto.request;

import finalmission.entity.Category;

public record CategoryRequest(
        String name,
        String description
) {
    public Category toCategory(){
        return new Category(name, description);
    }
}
