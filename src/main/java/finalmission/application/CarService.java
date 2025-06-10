package finalmission.application;

import finalmission.domain.Car;
import finalmission.domain.repository.CarRepository;
import finalmission.dto.RequestRegisterCar;
import finalmission.dto.ResponseCar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Long register(RequestRegisterCar requestRegisterCar) {
        Car car = Car.builder()
                .name(requestRegisterCar.name())
                .licensePlate(requestRegisterCar.licensePlate())
                .feePerHour(requestRegisterCar.feePerHour())
                .build();
        Car savedCar = carRepository.save(car);
        return savedCar.getId();
    }

    public List<ResponseCar> getAll() {
        return carRepository.findAll()
                .stream()
                .map(ResponseCar::from)
                .toList();
    }

    public void deleteById(Long carId) {
        carRepository.deleteById(carId);
    }
}
