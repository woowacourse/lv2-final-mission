package finalmission.restaurant.domain;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    void deleteById(Long id);

    Restaurant getById(Long id);

    List<Restaurant> findAll();
}
