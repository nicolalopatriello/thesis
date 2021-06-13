package it.nicolalopatriello.thesis.runner.watchers.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SimpleDockerInspectWatcherArgs implements WatcherArgs {
    private String serverAddress;
    private String dockerImage;
    private String dockerRunCmd;
}
