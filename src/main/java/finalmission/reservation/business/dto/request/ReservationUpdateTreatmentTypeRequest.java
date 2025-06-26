package finalmission.reservation.business.dto.request;

import finalmission.medical.model.TreatmentType;

public record ReservationUpdateTreatmentTypeRequest(Long id, TreatmentType treatmentType, String username) {

    public ReservationUpdateTreatmentTypeRequest {
        if (id == null) {
            throw new IllegalArgumentException("id는 null이 될 수 없습니다.");
        }
        if (treatmentType == null) {
            throw new IllegalArgumentException("진료 종류는 null이 될 수 없습니다.");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("예약자 아이디는 null이 될 수 없습니다.");
        }
    }
}
