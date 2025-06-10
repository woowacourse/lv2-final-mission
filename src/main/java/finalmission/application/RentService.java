package finalmission.application;

import finalmission.domain.Car;
import finalmission.domain.Member;
import finalmission.domain.Rent;
import finalmission.domain.repository.CarRepository;
import finalmission.domain.repository.RentRepository;
import finalmission.dto.RequestRent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentService {

    private final CarRepository carRepository;
    private final RentRepository rentRepository;

    @Transactional
    public Long rent(Member member, RequestRent requestRent) {
        Car car = getCar(requestRent);
        validateExistsTime(car, requestRent);
        Rent rent = createRent(member, car, requestRent);
        Rent savedRent = rentRepository.save(rent);
        return savedRent.getId();
    }

    private Car getCar(RequestRent requestRent) {
        return carRepository.findById(requestRent.carId())
                .orElseThrow(() -> new IllegalArgumentException("공유 차량이 존재하지 않습니다."));
    }

    private void validateExistsTime(Car car, RequestRent requestRent) {
        boolean existsRent = rentRepository.existsByCarAndDateAndStartTimeGreaterThanEqualAndReturnTimeLessThanEqual(
                car,
                requestRent.date(),
                requestRent.startTime(),
                requestRent.returnTime()
        );
        if (existsRent) {
            throw new IllegalArgumentException("이미 예약된 차량입니다. 다른 차량 혹은 시간대를 이용해주세요.");
        }
    }

    private Rent createRent(Member member, Car car, RequestRent requestRent) {
        return Rent.builder()
                .member(member)
                .car(car)
                .date(requestRent.date())
                .startTime(requestRent.startTime())
                .returnTime(requestRent.returnTime())
                .build();
    }
}
