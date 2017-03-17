package com.github.invader.controller.services;

import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.Trigger;

import java.time.LocalDateTime;
import java.util.Comparator;

public class TriggerComparator implements Comparator<Configuration> {

    @Override
    public int compare(Configuration c1, Configuration c2) {
        if(c1.getTrigger() == null) return c2.getTrigger() == null ? 0 : 1;
        if(c2.getTrigger() == null) return -1;
        return compareTriggers(c1.getTrigger(), c2.getTrigger());
    }

    private int compareTriggers(Trigger t1, Trigger t2) {
        int startsComparison = compare(t1.getStarts(), t2.getStarts(), true);
        return startsComparison != 0 ? startsComparison : compare(t1.getEnds(), t2.getEnds(), false);
    }

    private int compare(LocalDateTime t1, LocalDateTime t2, boolean desc) {
        if(t1 == null) return t2 == null ? 0 : 1;
        if(t2 == null) return -1;
        return desc ? t2.compareTo(t1) : t1.compareTo(t2);
    }

}
