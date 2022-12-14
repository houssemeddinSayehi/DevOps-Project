package com.esprit.examen.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

	@Autowired
	StockRepository stockRepository;


	@Override
	public List<Stock> retrieveAllStocks() {
		// récuperer la date à l'instant t1
		log.info("In method retrieveAllStocks");

		for (Stock stock : stockRepository.findAll()) {
			log.info(" Stock : " + stock);
		}
		log.info("out of method retrieveAllStocks");
		// récuperer la date à l'instant t2
		// temps execution = t2 - t1
		return stockRepository.findAll();
	}

	@Override
	public Stock addStock(Stock s) {
		// récuperer la date à l'instant t1
		log.info("In method addStock");
		return stockRepository.save(s);
		
	}

	@Override
	public void deleteStock(Long stockId) {
		log.info("In method deleteStock");
		stockRepository.deleteById(stockId);

	}

	@Override
	public Stock updateStock(Stock s) {
		log.info("In method updateStock");
		return stockRepository.save(s);
	}

	@Override
	public Stock retrieveStock(Long stockId) {
		long start = System.currentTimeMillis();
		log.info("In method retrieveStock");
		Stock stock = stockRepository.findById(stockId).orElse(null);
		log.info("out of method retrieveStock");
		 long elapsedTime = System.currentTimeMillis() - start;
		log.info("Method execution time: " + elapsedTime + " milliseconds.");

		return stock;
	}

	@Override
	public String retrieveStatusStock() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String msgDate = sdf.format(now);
		StringBuilder finalMessage = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		for (int i = 0; i < stockRepository.retrieveStatusStock().size(); i++) {
			finalMessage = new StringBuilder(newLine + finalMessage + msgDate + newLine + ": le stock "
					+ stockRepository.retrieveStatusStock().get(i).getLibelleStock() + " a une quantité de " + stockRepository.retrieveStatusStock().get(i).getQte()
					+ " inférieur à la quantité minimale a ne pas dépasser de " + stockRepository.retrieveStatusStock().get(i).getQteMin()
					+ newLine);

		}
		log.info(finalMessage.toString());
		return finalMessage.toString();
	}

}