package com.psybergate.absaassessment.mpatho.api.web.rest;

import com.psybergate.absaassessment.mpatho.api.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BillRestController {

    private final BillService billService;

    @PostMapping("/{year}/{month}")
    public ResponseEntity<String> createBillingFile(@RequestBody String content, @PathVariable Integer year, @PathVariable Integer month) {
        billService.save(year, month, content);
        return ResponseEntity.ok("Upload was successful");
    }
}
