public enum ModuleType {
    H((float)0.5, "High speed transit - optimized"),
    B((float)1, "Balanced"),
    S((float)2, "Storage access time - optimized"),
    O(Float.POSITIVE_INFINITY, "Out of service");

    final float rideTime;
    final String typeName;

    ModuleType(float rideTime, String typeName) {
        this.rideTime = rideTime;
        this.typeName = typeName;
    }

    public float getRideTime() {
        return rideTime;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString(){
        return typeName;
    }
}
