package it.nicolalopatriello.thesis.common.dto;

import it.nicolalopatriello.thesis.common.utils.WatcherType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Recipe {
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        WatcherType watcherType;
        Object args;
    }
}
