package devrodrigues.model.enums;

import java.util.Set;

public enum BookingStatus {

    CREATED {
        @Override
        public Set<BookingStatus> allowedTransitions() {
            return Set.of(CANCELED, FINISHED);
        }
    },

    CANCELED {
        @Override
        public Set<BookingStatus> allowedTransitions() {
            return Set.of(); // não permite transições
        }
    },

    FINISHED {
        @Override
        public Set<BookingStatus> allowedTransitions() {
            return Set.of(); // não permite transições
        }
    };

    // Força cada constante a definir suas transições válidas
    public abstract Set<BookingStatus> allowedTransitions();

    // Checa se a transição é permitida
    public boolean canTransitionTo(BookingStatus nextStatus) {
        return allowedTransitions().contains(nextStatus);
    }

    public BookingStatus transitionTo(BookingStatus nextStatus) {
        if (!canTransitionTo(nextStatus)) {
            throw new IllegalStateException(
                    "Transição inválida de " + this + " para " + nextStatus
            );
        }
        return nextStatus;
    }
}
