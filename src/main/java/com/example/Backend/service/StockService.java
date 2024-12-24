package com.example.Backend.service;

import com.example.Backend.exception.ResourceNotFoundException;
import com.example.Backend.model.Stock;
import com.example.Backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockPriceService stockPriceService;

    public Stock addStock(Stock stock) {
        validateStock(stock);
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock stockDetails) {
        validateStock(stockDetails);

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));

        stock.setSymbol(stockDetails.getSymbol());
        stock.setName(stockDetails.getName());
        stock.setQuantity(stockDetails.getQuantity());
        stock.setBuyPrice(stockDetails.getBuyPrice());
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
        stockRepository.delete(stock);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public BigDecimal calculatePortfolioValue() {
        return getAllStocks().stream()
                .map(stock -> stock.getBuyPrice().multiply(BigDecimal.valueOf(stock.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateStock(Stock stock) {
        if (stock.getSymbol() == null || stock.getSymbol().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol is required");
        }
        if (stock.getQuantity() <= 0) {
            throw new IllegalArgumentException("Stock quantity must be greater than zero");
        }
        if (stock.getBuyPrice() == null) {
            throw new IllegalArgumentException("Stock buy price is required");
        }
    }
}
