package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.types.AccountStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountStateFactory {

    private final ActiveState activeState;
    private final SuspendedState suspendedState;
    private final ClosedState closedState;

    public AccountStateFactory(ActiveState activeState, SuspendedState suspendedState, ClosedState closedState) {
        this.activeState = activeState;
        this.suspendedState = suspendedState;
        this.closedState = closedState;
    }

    public AccountState getState(AccountStatus status) {
        return switch (status) {
            case ACTIVE -> activeState;
            case SUSPENDED -> suspendedState;
            case CLOSED -> closedState;
        };
    }
}

