package finalmission.presentation;

import finalmission.application.CarService;
import finalmission.dto.RequestRegisterCar;
import finalmission.dto.ResponseCar;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cars")
@RestController
@RequiredArgsConstructor
public class CarController {

    private static final String CARS_URI_FORMAT = "/cars/%d";

    private final CarService carService;

    @PostMapping
    public ResponseEntity<Void> registerCar(@RequestBody RequestRegisterCar requestRegisterCar) {
        Long id = carService.register(requestRegisterCar);
        return ResponseEntity.created(URI.create(CARS_URI_FORMAT.formatted(id))).build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseCar>> getCars() {
        return ResponseEntity.ok(carService.getAll());
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable(name = "carId") Long carId) {
        carService.deleteById(carId);
        return ResponseEntity.noContent().build();
    }
}
