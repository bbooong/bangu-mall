package com.github.bbooong.bangumall.payment.ui;

import com.github.bbooong.bangumall.auth.domain.Authorities;
import com.github.bbooong.bangumall.auth.domain.MemberRole;
import com.github.bbooong.bangumall.payment.application.PaymentService;
import com.github.bbooong.bangumall.payment.application.dto.PaymentCreateRequest;
import com.github.bbooong.bangumall.payment.application.dto.PaymentInfoResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 요청
    @PostMapping
    @Authorities(MemberRole.CUSTOMER)
    public ResponseEntity<Void> requestPayment(
            @RequestBody @Valid final PaymentCreateRequest request) {
        final long paymentId = paymentService.requestPayment(request);

        return ResponseEntity.created(URI.create("/payments/" + paymentId)).build();
    }

    // 결제 확인
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Authorities(MemberRole.CUSTOMER)
    public PaymentInfoResponse getPayment(@PathVariable final String id) {
        return new PaymentInfoResponse(1000, null);
    }

    // 결제 취소
}