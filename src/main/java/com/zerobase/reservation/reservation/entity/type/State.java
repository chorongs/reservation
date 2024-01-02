package com.zerobase.reservation.reservation.entity.type;

import lombok.Getter;

@Getter
public enum State {

    REQUEST("REQUEST"),
    CONFIRM("CONFIRM"),
    CANCEL("CANCEL");

    private final String state;

    State(String state) {
        this.state = state;
    }

    public static State get(String stateText) {

        for (State s : State.values()) {
            if (s.getState().equals(stateText)) {
                return s;
            }
        }
        return null;
    }

}
