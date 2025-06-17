package finalmission.dateprice.service;

import finalmission.accommodation.domain.Accommodation;
import finalmission.accommodation.repository.AccommodationRepository;
import finalmission.dateprice.domain.DatePrice;
import finalmission.dateprice.dto.AddDatePriceRequest;
import finalmission.dateprice.dto.DatePriceResponse;
import finalmission.dateprice.repository.DatePriceRepository;
import finalmission.global.DataNotFoundException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class DatePriceService {

    private final DatePriceRepository datePriceRepository;
    private final AccommodationRepository accommodationRepository;

    public DatePriceService(DatePriceRepository datePriceRepository, AccommodationRepository accommodationRepository) {
        this.datePriceRepository = datePriceRepository;
        this.accommodationRepository = accommodationRepository;
    }

    public DatePriceResponse create(AddDatePriceRequest request) {
        Accommodation accommodation = accommodationRepository.findById(request.accommodationId())
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 숙소입니다."));

        DatePrice datePrice = new DatePrice(request.date(), request.price(), accommodation);
        return DatePriceResponse.of(datePriceRepository.save(datePrice));
    }

    public long calculateTotalPrice(LocalDate startDate, LocalDate endDate) {
        long totalPrice = 0;

        LocalDate date = startDate;
        while (date.isBefore(endDate)) {
            DatePrice datePrice = datePriceRepository.findByDate(date)
                    .orElseThrow(() -> new DataNotFoundException("가격 정보가 존재하지 않는 날짜입니다."));
            totalPrice += datePrice.getPrice();
            date = date.plusDays(1);
        }

        return totalPrice;
    }
}
