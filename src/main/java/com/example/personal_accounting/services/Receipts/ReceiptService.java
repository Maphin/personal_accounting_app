package com.example.personal_accounting.services.Receipts;

import com.example.personal_accounting.models.Receipt;
import com.example.personal_accounting.models.Transaction;
import com.example.personal_accounting.repository.ReceiptRepository;
import com.example.personal_accounting.repository.TransactionRepository;
import com.example.personal_accounting.utils.exceptions.ReceiptNotFoundException;
import com.example.personal_accounting.utils.exceptions.TransactionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final FileStorageConfig fileStorageConfig;
    private final ReceiptRepository receiptRepository;
    private final TransactionRepository transactionRepository;

    public Receipt uploadReceipt(MultipartFile file, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(fileStorageConfig.getUploadDir(), fileName);

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        Receipt receipt = new Receipt();
        receipt.setTransaction(transaction);
        receipt.setPath(filePath.toString());
        return receiptRepository.save(receipt);
    }

    public byte[] getReceiptFile(Long receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ReceiptNotFoundException("Receipt not found"));

        try {
            return Files.readAllBytes(Paths.get(receipt.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }

    public void deleteReceipt(Long receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ReceiptNotFoundException("Receipt not found"));

        try {
            Path filePath = Paths.get(receipt.getPath());
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }

        receiptRepository.delete(receipt);
    }
}

