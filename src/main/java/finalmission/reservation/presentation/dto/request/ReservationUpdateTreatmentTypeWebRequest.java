package finalmission.reservation.presentation.dto.request;

import finalmission.medical.model.TreatmentType;

public record ReservationUpdateTreatmentTypeWebRequest(TreatmentType treatmentType) {

    public ReservationUpdateTreatmentTypeWebRequest {
        if (treatmentType == null) {
            throw new IllegalArgumentException("진료 종류는 null이 될 수 없습니다.");
        }
    }
}
