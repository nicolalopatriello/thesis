package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.exception.CveDetailsClientException;

import java.util.List;

public interface DependencyService {
    void save(DependencyEntity d) throws CveDetailsClientException;
}
