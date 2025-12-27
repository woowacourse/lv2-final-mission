package library.borrow.controller;

import library.borrow.dto.BorrowResponse;
import library.borrow.service.BorrowService;
import library.reservation.dto.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/collection/{id}/borrow")
    public ResponseEntity<BorrowResponse> borrowBook(
            @PathVariable Long id, 
            @RequestBody MemberRequest memberRequest) {
        
        BorrowResponse response = borrowService.borrowBook(id, memberRequest);
        return ResponseEntity.ok(response);
    }

}