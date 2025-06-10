package finalmission.application;

import finalmission.domain.Car;
import finalmission.domain.Member;
import finalmission.domain.Rent;
import finalmission.domain.repository.CarRepository;
import finalmission.domain.repository.RentRepository;
import finalmission.dto.RequestRent;
import finalmission.dto.ResponseRent;
import finalmission.dto.ResponseRentDetail;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<ResponseRent> getAll() {
        return rentRepository.findAllWithCar()
                .stream()
                .map(ResponseRent::from)
                .toList();
    }

    @Transactional
    public List<ResponseRentDetail> getAllByMember(Member member) {
        return rentRepository.findAllByMember(member)
                .stream()
                .map(ResponseRentDetail::from)
                .toList();
    }

    @Transactional
    public void cancelById(Member member, Long rentId) {
        Rent rent = rentRepository.findByIdWithCarAndMember(rentId)
                .orElseThrow(() -> new IllegalArgumentException("렌트 이력을 찾을 수 없습니다."));
        boolean cancelable = rent.canBeCanceledBy(member);
        if (!cancelable) {
            throw new IllegalArgumentException("본인 예약만 취소할 수 있습니다.");
        }
        rentRepository.delete(rent);
    }
}
