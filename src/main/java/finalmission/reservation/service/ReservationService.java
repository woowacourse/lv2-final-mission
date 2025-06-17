package finalmission.reservation.service;

import finalmission.member.exception.UserBadRequestException;
import finalmission.reservation.Reservation;
import finalmission.reservation.domain.dto.ReservationRequestDto;
import finalmission.reservation.domain.dto.ReservationResponseDto;
import finalmission.reservation.exception.ReservationBadRequestException;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.room.domain.Room;
import finalmission.room.exception.RoomBadRequestException;
import finalmission.room.repository.RoomRepository;
import finalmission.user.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public ReservationResponseDto create(ReservationRequestDto requestDto, User member) {
        Reservation reservation = convertToReservation(requestDto, member);
        Reservation savedReservation = save(reservation);
        return convertToReservationResponseDto(savedReservation);
    }

    private Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<ReservationResponseDto> findAllByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomBadRequestException::new);
        List<Reservation> reservations = reservationRepository.findAllByRoom(room);

        return convertToReservationResponseDtos(reservations);
    }

    public List<ReservationResponseDto> findAllByUser(User member) {
        List<Reservation> reservations = reservationRepository.findAllByUser(member);

        return convertToReservationResponseDtos(reservations);
    }

    public ReservationResponseDto update(Long reservationId, ReservationRequestDto requestDto, User user) {
        Reservation reservation = getReservationById(reservationId);
        validateOwnReservation(user, reservation);

        reservation.update(requestDto);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToReservationResponseDto(savedReservation);
    }

    public void deleteById(Long reservationId, User user) {
        Reservation reservation = getReservationById(reservationId);
        validateOwnReservation(user, reservation);

        reservationRepository.deleteById(reservationId);
    }

    private static void validateOwnReservation(User user, Reservation reservation) {
        if (!reservation.getUser().equals(user)) {
            throw new UserBadRequestException();
        }
    }

    private Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(ReservationBadRequestException::new);
    }

    private Reservation convertToReservation(ReservationRequestDto requestDto, User member) {
        Room room = roomRepository.findById(requestDto.roomId())
                .orElseThrow(RoomBadRequestException::new);
        return requestDto.toEntity(room, member);
    }

    private ReservationResponseDto convertToReservationResponseDto(Reservation savedReservation) {
        return ReservationResponseDto.of(savedReservation, savedReservation.getRoom(), savedReservation.getUser());
    }

    private List<ReservationResponseDto> convertToReservationResponseDtos(List<Reservation> reservations) {
        return reservations.stream().map(this::convertToReservationResponseDto)
                .collect(Collectors.toList());
    }
}
