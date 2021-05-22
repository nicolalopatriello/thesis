package it.nicolalopatriello.thesis.core.dto.watcher;


import it.nicolalopatriello.thesis.common.utils.WatcherType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LightWatcherConfig {
    private WatcherType type;
    private Long minutesInterval;
}
