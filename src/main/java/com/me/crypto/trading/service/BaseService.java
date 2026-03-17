package com.me.crypto.trading.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {
    @Autowired private ModelMapper modelMapper;
}
