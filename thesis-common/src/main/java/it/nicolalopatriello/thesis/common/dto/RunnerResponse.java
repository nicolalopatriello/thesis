package it.nicolalopatriello.thesis.common.dto;

import it.nicolalopatriello.thesis.common.Jsonizable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RunnerResponse extends Jsonizable {
    private Long repositoryId;
    private String commitSha;
    private List<WatcherResponse> watchers;
}
