const RESERVATION_API_ENDPOINT = '/reservations';
const PAYMENT_API_ENDPOINT = '/payments';

document.addEventListener('DOMContentLoaded', () => {

    document.getElementById('pay-btn').addEventListener('click', onPaymentButtonClick);
    document.getElementById('reserve-btn').addEventListener('click', onReservationButtonClick);
});

function onPaymentButtonClick() {
    const selectedConcertId = document.getElementById('concert-id').value;
    const selectedSeatId = document.getElementById('seat-id').value;

    const concertId = parseInt(selectedConcertId, 10);
    const seatId = parseInt(selectedSeatId, 10);

    const paymentReadyRequest = {
        concertId: concertId,
        seatId: seatId,
    }

    fetch(PAYMENT_API_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(paymentReadyRequest),
    }).then(response => {
        if (!response.ok) {
            return response.json().then(errorBody => {
                console.error("ready 실패 : " + JSON.stringify(errorBody));
            });
        } else {
            response.json().then(successBody => {
                console.log("결제 준비 성공: " + JSON.stringify(successBody));
                const nextRedirectUrl = successBody.nextRedirectUrl;
                document.getElementById('tid').value = successBody.tid;
                const popupWindow = window.open(nextRedirectUrl, 'kakaoPayPopup', 'width=450,height=600,scrollbars=yes,resizable=no');
            });
        }
    });
}

function onReservationButtonClick() {
    const selectedConcertId = document.getElementById('concert-id').value;
    const selectedSeatId = document.getElementById('seat-id').value;
    const tid = document.getElementById('tid').value;
    const pgToken = document.getElementById('token-id').value;

    const concertId = parseInt(selectedConcertId, 10);
    const seatId = parseInt(selectedSeatId, 10);

    const paymentReadyRequest = {
        concertId: concertId,
        seatId: seatId,
        tid: tid,
        pgToken: pgToken,
    }

    fetch(RESERVATION_API_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(paymentReadyRequest),
    }).then(response => {
        if (!response.ok) {
            return response.json().then(errorBody => {
                console.error("예약 실패 : " + JSON.stringify(errorBody));
            });
        } else {
            return response.json().then(successBody => {
                console.log("예약 성공 : " + JSON.stringify(successBody));
            });
        }
    });
}
