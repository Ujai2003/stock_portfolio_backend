package com.example.Backend.controller;

import com.example.Backend.model.Stock;
import com.example.Backend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "https://vercel.com/ujais-projects-d9a0eb17/stock-portfolio-frontend") // Allow requests from your frontend URL
public class StockController {

    @Autowired
    private StockService stockService;

    // Add Stock
    @PostMapping("/add")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        // Ensure the Stock object includes symbol
        if (stock.getSymbol() == null || stock.getSymbol().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(stockService.addStock(stock));
    }

    // Update Stock
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stockDetails) {
        // Ensure symbol and other details are validated
        if (stockDetails.getSymbol() == null || stockDetails.getSymbol().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(stockService.updateStock(id, stockDetails));
    }

    // Delete Stock
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    // Get All Stocks
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    // Get Portfolio Value
    @GetMapping("/portfolio-value")
    public ResponseEntity<BigDecimal> getPortfolioValue() {
        return ResponseEntity.ok(stockService.calculatePortfolioValue());
    }
}
