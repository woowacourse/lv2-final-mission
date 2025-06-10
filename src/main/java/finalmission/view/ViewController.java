package finalmission.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String displayIndexPage() {
        return "index";
    }

    @GetMapping("/reservation")
    public String displayReservationPage() {
        return "reservation";
    }

    @GetMapping("/login")
    public String displayLoginPage() {
        return "login";
    }

    @GetMapping("/reservation-mine")
    public String displayMyReservationPage() {
        return "reservation-mine";
    }
}
