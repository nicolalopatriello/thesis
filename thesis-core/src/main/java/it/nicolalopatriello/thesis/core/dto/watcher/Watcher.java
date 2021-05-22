//package it.nicolalopatriello.thesis.core.dto.watcher;
//
//
//import it.nicolalopatriello.thesis.common.utils.WatcherType;
//import it.nicolalopatriello.thesis.core.spring.dto.DTO;
//import it.nicolalopatriello.thesis.core.entities.WatcherEntity;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class Watcher extends DTO {
//
//    private Long id;
//    private WatcherType type;
//    private Boolean enabled;
//    private Long minutesInterval;
//    private Long repositoryId;
//    private Long lastUpdate;
//
//    public static Watcher of(WatcherEntity watcherEntity) {
//        Watcher dto = new Watcher();
//        dto.setId(watcherEntity.getId());
//        dto.setType(watcherEntity.getType());
//        dto.setEnabled(watcherEntity.getEnabled());
//        dto.setMinutesInterval(watcherEntity.getMinutesInterval());
//        dto.setRepositoryId(watcherEntity.getRepositoryId());
//        if (watcherEntity.getLastUpdate() != null)
//            dto.setLastUpdate(watcherEntity.getLastUpdate().getTime());
//        return dto;
//    }
//
//    public WatcherEntity to() {
//        WatcherEntity watcherEntity = new WatcherEntity();
//        watcherEntity.setId(id);
//        watcherEntity.setType(type);
//        watcherEntity.setEnabled(enabled);
//        watcherEntity.setMinutesInterval(minutesInterval);
//        watcherEntity.setRepositoryId(repositoryId);
//        if (watcherEntity.getLastUpdate() != null)
//            watcherEntity.setLastUpdate(watcherEntity.getLastUpdate());
//        return watcherEntity;
//    }
//
//    public static LightWatcherConfig from(WatcherEntity watcherEntity) {
//        LightWatcherConfig lightWatcherConfig = new LightWatcherConfig();
//        lightWatcherConfig.setType(watcherEntity.getType());
//        lightWatcherConfig.setMinutesInterval(watcherEntity.getMinutesInterval());
//        return lightWatcherConfig;
//    }
//}
