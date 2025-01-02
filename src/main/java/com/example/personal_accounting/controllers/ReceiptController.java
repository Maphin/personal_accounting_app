package com.example.personal_accounting.controllers;

import com.example.personal_accounting.models.Receipt;
import com.example.personal_accounting.services.Receipts.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping("/upload")
    public ResponseEntity<Receipt> uploadReceipt(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("transactionId") Long transactionId) {
        Receipt receipt = receiptService.uploadReceipt(file, transactionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long id) {
        byte[] fileData = receiptService.getReceiptFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"receipt.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileData);
    }
}

