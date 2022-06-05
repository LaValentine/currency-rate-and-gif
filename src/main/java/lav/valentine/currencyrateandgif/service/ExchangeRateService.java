package lav.valentine.currencyrateandgif.service;

import lav.valentine.currencyrateandgif.dto.ResponseDto;

public interface ExchangeRateService {

    ResponseDto getData(String currency);
}