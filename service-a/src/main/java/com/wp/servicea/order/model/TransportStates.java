package com.wp.servicea.order.model;

/**
 * Represents internal processing states (T_LOGISTIC_STATES)
 * otherwise legs
 */
public enum TransportStates {
    RECEIVED,
    PROCESSED_BY_LOGISTIC_SERVICE,
    PROCESSED_BY_SUPER_SERVICE,
    SUCCESS,
    FAILED
}
