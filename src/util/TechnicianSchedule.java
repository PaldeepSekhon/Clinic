package util;

import java.util.Objects;

public class TechnicianSchedule {
    private String technicianName;
    private Date appointmentDate;
    private Timeslot timeslot;

    public TechnicianSchedule(String technicianName, Date appointmentDate, Timeslot timeslot) {
        this.technicianName = technicianName;
        this.appointmentDate = appointmentDate;
        this.timeslot = timeslot;
    }

    // Override equals and hashCode to ensure uniqueness
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TechnicianSchedule that = (TechnicianSchedule) obj;
        return technicianName.equals(that.technicianName) && appointmentDate.equals(that.appointmentDate) && timeslot.equals(that.timeslot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(technicianName, appointmentDate, timeslot);
    }
}
