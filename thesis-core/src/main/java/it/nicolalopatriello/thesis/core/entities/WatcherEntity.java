//package it.nicolalopatriello.thesis.core.entities;
//
//import it.nicolalopatriello.thesis.core.spring.dto.WithDTO;
//import it.nicolalopatriello.thesis.core.jpa.SearchCriteria;
//import it.nicolalopatriello.thesis.core.jpa.SimpleSearchSpecification;
//import it.nicolalopatriello.thesis.core.Schema;
//import it.nicolalopatriello.thesis.common.utils.WatcherType;
//import lombok.Data;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.sql.Timestamp;
//
//@Entity
//@Table(name = "gitrace", schema = Schema.SCHEMA_NAME)
//@Data
//public class WatcherEntity implements WithDTO<Watcher> {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
//    private Long id;
//
//    @NotNull
//    @Column
//    private WatcherType type;
//
//    @Column
//    private Boolean enabled;
//
//    @NotNull
//    @Column(name = "minutes_interval")
//    private Long minutesInterval;
//
//    @NotNull
//    @Column(name = "repository_id")
//    private Long repositoryId;
//
//    @Column(name = "last_update")
//    private Timestamp lastUpdate;
//
//
//    @Override
//    public Watcher dto() {
//        Watcher watcher = new Watcher();
//        watcher.setId(id);
//        watcher.setType(type);
//        watcher.setEnabled(enabled);
//        watcher.setMinutesInterval(minutesInterval);
//        watcher.setRepositoryId(repositoryId);
//        watcher.setLastUpdate(lastUpdate.getTime());
//        return watcher;
//    }
//
//    public static class Specification extends SimpleSearchSpecification<WatcherEntity> {
//        public Specification(SearchCriteria criteria) {
//            super(criteria);
//        }
//    }
//}