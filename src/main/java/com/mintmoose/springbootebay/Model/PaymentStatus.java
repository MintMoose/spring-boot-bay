package com.mintmoose.springbootebay.Model;

public enum PaymentStatus {
    PAID("Paid"),
    PENDING("Pending"),
    FAILED("Failed");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

