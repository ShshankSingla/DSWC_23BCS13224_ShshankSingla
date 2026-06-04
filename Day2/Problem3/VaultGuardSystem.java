enum DoorState {
    OPEN,
    CLOSED,
    LOCKED
}

class IllegalStateTransitionException
        extends RuntimeException {

    public IllegalStateTransitionException(String message) {
        super(message);
    }
}

class VaultDoor {

    private DoorState state;

    public VaultDoor() {
        state = DoorState.OPEN;
    }

    public void closeDoor() {

        if (state == DoorState.OPEN) {
            state = DoorState.CLOSED;
            System.out.println("Door is now CLOSED.");
        } else {
            System.out.println("Door cannot be closed.");
        }
    }

    public void lockDoor() {

        if (state == DoorState.OPEN) {
            throw new IllegalStateTransitionException(
                    "Cannot lock the door while it is OPEN."
            );
        }

        if (state == DoorState.CLOSED) {
            state = DoorState.LOCKED;
            System.out.println("Door is now LOCKED.");
        } else {
            System.out.println("Door is already LOCKED.");
        }
    }

    public void unlockDoor() {

        if (state == DoorState.LOCKED) {
            state = DoorState.CLOSED;
            System.out.println("Door is now UNLOCKED.");
        } else {
            System.out.println("Door is not locked.");
        }
    }

    public DoorState getState() {
        return state;
    }
}

public class VaultGuardSystem {

    public static void main(String[] args) {

        VaultDoor vaultDoor = new VaultDoor();

        try {

            vaultDoor.lockDoor();

        } catch (IllegalStateTransitionException e) {

            System.out.println(e.getMessage());
        }

        vaultDoor.closeDoor();

        vaultDoor.lockDoor();

        vaultDoor.unlockDoor();
    }
}