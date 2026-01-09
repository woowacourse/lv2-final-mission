package finalmission.web;

import finalmission.auth.AuthMember;
import finalmission.member.Member;
import finalmission.meetingroom.MeetingRoomService;
import finalmission.reservation.ReservationService;
import finalmission.reservation.ReservationResponse;
import finalmission.reservation.MeetingRoomTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final MeetingRoomService meetingRoomService;
    private final ReservationService reservationService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/meetingroom")
    public String meetingRooms(Model model) {
        return "meetingrooms";
    }

    @GetMapping("/reservation")
    public String reservations(@RequestParam(value = "meetingroom", required = false) Long meetingRoomId,
                              @RequestParam(value = "date", required = false) String dateStr,
                              Model model) {
        if (meetingRoomId != null && dateStr != null) {
            LocalDate date = LocalDate.parse(dateStr);
            List<MeetingRoomTimeResponse> availableTimes = reservationService.getAvailableTimes(meetingRoomId, date);
            model.addAttribute("availableTimes", availableTimes);
            model.addAttribute("selectedMeetingRoomId", meetingRoomId);
            model.addAttribute("selectedDate", dateStr);
        }
        return "reservations";
    }

    @GetMapping("/my-reservation")
    public String myReservations(@AuthMember Member member, Model model) {
        List<ReservationResponse> myReservations = reservationService.getByMember(member);
        model.addAttribute("reservations", myReservations);
        return "my-reservations";
    }
} 
