package devrodrigues.model.enums;

import java.util.Set;

public enum VehicleStatus {

    AVAILABLE {
        @Override
        public Set<VehicleStatus> allowedTransitions() {
            return Set.of(RENTED, UNDER_MAINTENANCE);
        }
    },
    RENTED {
        @Override
        public Set<VehicleStatus> allowedTransitions() {
            return Set.of(AVAILABLE, UNDER_MAINTENANCE);
        }
    },
    UNDER_MAINTENANCE {
        @Override
        public Set<VehicleStatus> allowedTransitions() {
            return Set.of(AVAILABLE);
        }
    };

    // Método abstrato para forçar cada constante a definir suas transições válidas
    public abstract Set<VehicleStatus> allowedTransitions();

    // Método para checar se uma transição é permitida
    public boolean canTransitionTo(VehicleStatus nextStatus) {
        return allowedTransitions().contains(nextStatus);
    }
}