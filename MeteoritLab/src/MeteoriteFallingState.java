public enum MeteoriteFallingState {
    fell, found;

    public boolean isFell() {
        return this == fell;
    }

    public boolean isFound() {
        return this == found;
    }
}
