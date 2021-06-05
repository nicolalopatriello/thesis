package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.Statistic;
import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import it.nicolalopatriello.thesis.core.exception.CveDetailsClientException;

public interface StatisticService {
    Statistic find(String username) throws NotFoundException;
}
