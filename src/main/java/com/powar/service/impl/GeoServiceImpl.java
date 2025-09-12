package com.powar.service.impl;

import com.powar.entity.MasterState;
import com.powar.repository.MasterStateRepository;
import com.powar.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoServiceImpl implements GeoService {

    @Autowired
    private MasterStateRepository masterStateRepository;

    @Override
    public List<MasterState> getMasterState() {
        return masterStateRepository.findAllByOrderByStateNameAsc();
    }
}
