package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.core.entities.MetricEntity;
import it.nicolalopatriello.thesis.core.repos.MetricRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Log4j
@Service
public class MetricServiceImpl implements MetricService {

    @Autowired
    MetricRepository metricRepository;

    @Override
    public void save(MetricEntity d) {
        metricRepository.save(d);
    }
}
