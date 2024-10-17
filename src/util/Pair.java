package util;

public class Pair {
    private String location; // Changed to String
    private Timeslot timeslot;

    public Pair(String location, Timeslot timeslot) {
        this.location = location;
        this.timeslot = timeslot;
    }

    public String getLocation() {
        return location;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pair pair = (Pair) obj;
        return location.equals(pair.location) && timeslot.equals(pair.timeslot);
    }

    @Override
    public int hashCode() {
        return 31 * location.hashCode() + timeslot.hashCode();
    }
}
