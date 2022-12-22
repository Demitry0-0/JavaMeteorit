public enum MeteoriteFallingState {
    fell, found;

    public boolean isFell() {
        return this == MeteoriteFallingState.fell;
    }

    public boolean isFound() {
        return this == MeteoriteFallingState.found;
    }
}
