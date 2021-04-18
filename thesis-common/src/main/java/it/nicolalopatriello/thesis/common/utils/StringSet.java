package it.nicolalopatriello.thesis.common.utils;

import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class StringSet extends HashSet<String> {
    public StringSet(Set<String> itemMatches) {
       this.addAll(itemMatches);
    }
}
