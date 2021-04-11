package it.nicolalopatriello.thesis.common.utils;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 11/25/20.
 */

import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class StringSet extends HashSet<String> {
    public StringSet(Set<String> itemMatches) {
       this.addAll(itemMatches);
    }
}
