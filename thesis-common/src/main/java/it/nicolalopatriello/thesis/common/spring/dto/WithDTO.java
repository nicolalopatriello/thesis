package it.nicolalopatriello.thesis.common.spring.dto;

public interface WithDTO<E extends DTO> {
    E dto();
}
