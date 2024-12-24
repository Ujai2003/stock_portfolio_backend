package com.example.Backend.service;

import com.example.Backend.model.Stock;
import com.example.Backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockPriceService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getStocks() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            stock.setCurrentPrice(BigDecimal.ZERO); // Set a default value
        }
        return stocks;
    }

    public BigDecimal fetchCurrentPrice(String symbol) {
        // Remove API call and return default price
        return BigDecimal.ZERO;
    }
}
