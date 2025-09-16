package org.ilestegor.lab1.model;

public enum Priority {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");
    private final String priority;

    Priority(String pr) {
        priority = pr;
    }

    public String getPriority() {
        return priority;
    }
}
