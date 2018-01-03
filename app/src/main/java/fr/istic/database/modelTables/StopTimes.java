package fr.istic.database.modelTables;

/**
 * Created by Bah on 19/11/2017.
 */

public class StopTimes {

    private int tripId;
    private String arrivalTime;
    private String departureTime;
    private int stopId;
    private String stopSequence;
    private  int _ID;

    public StopTimes(int tripId, String arrivalTime, String departureTme, int stopId, String stopSequence) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTme;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
    }

    public StopTimes(int id, int tripId, String arrivalTime, String departureTme, int stopId, String stopSequence) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTme;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
        this._ID =  id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(String stopSequence) {
        this.stopSequence = stopSequence;
    }

    @Override
    public String toString() {
        return "StopeTimes{" +
                "tripId=" + tripId +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", stopId=" + stopId +
                ", stopSequence='" + stopSequence + '\'' +
                '}';
    }
}
