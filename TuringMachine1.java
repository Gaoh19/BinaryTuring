import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TuringMachine1 {

    private Map<StateSymbol, Transition> transitions;
    private String tape;
    private int headPosition;
    private State currentState;

    public TuringMachine1(String input) {
        tape = "1" + input; // Adding a '1' at the beginning to ensure that the machine can handle the carry.
        headPosition = 1;
        currentState = State.Q0;
        transitions = new HashMap<>();

        // Define the transitions for the Turing machine
        transitions.put(new StateSymbol(State.Q0, '0'), new Transition(State.Q0, '0', Direction.RIGHT));
        transitions.put(new StateSymbol(State.Q0, '1'), new Transition(State.Q1, '1', Direction.RIGHT));

        transitions.put(new StateSymbol(State.Q1, '0'), new Transition(State.Q2, '1', Direction.LEFT));
        transitions.put(new StateSymbol(State.Q1, '1'), new Transition(State.Q1, '0', Direction.RIGHT));

        transitions.put(new StateSymbol(State.Q2, '0'), new Transition(State.Q2, '0', Direction.LEFT));
        transitions.put(new StateSymbol(State.Q2, '1'), new Transition(State.Q2, '1', Direction.LEFT));
    }

    public void run() {
        while (currentState != State.HALT) {
            char currentSymbol = tape.charAt(headPosition);
            StateSymbol stateSymbol = new StateSymbol(currentState, currentSymbol);
            Transition transition = transitions.get(stateSymbol);

            if (transition == null) {
                break;
            }

            tape = tape.substring(0, headPosition) + transition.writeSymbol + tape.substring(headPosition + 1);
            headPosition += transition.moveDirection.getOffset();
            currentState = transition.nextState;
        }

        // Remove leading '1' added for carry handling
        tape = tape.substring(1);
    }

    public String getOutput() {
        return tape;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a binary number: ");
        String input = scanner.nextLine();
        scanner.close();

        TuringMachine1 tm = new TuringMachine1(input);
        tm.run();
        String result = tm.getOutput();
        System.out.println("Incremented binary number: " + result);
    }

    enum State {
        Q0, Q1, Q2, HALT
    }

    enum Direction {
        LEFT(-1), RIGHT(1);

        private int offset;

        Direction(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return offset;
        }
    }

    class Transition {
        State nextState;
        char writeSymbol;
        Direction moveDirection;

        Transition(State nextState, char writeSymbol, Direction moveDirection) {
            this.nextState = nextState;
            this.writeSymbol = writeSymbol;
            this.moveDirection = moveDirection;
        }
    }

    class StateSymbol {
        State state;
        char symbol;

        StateSymbol(State state, char symbol) {
            this.state = state;
            this.symbol = symbol;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            StateSymbol that = (StateSymbol) obj;
            return symbol == that.symbol && state == that.state;
        }

        @Override
        public int hashCode() {
            return 31 * state.hashCode() + symbol;
        }
    }
}