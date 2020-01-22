package mobile;

public class RetrieveRunnerActivity {


    private String elapsed_time;
    private String total_distance;
    private String date;
    private String Id;
    private String avgSpeed;

    public RetrieveRunnerActivity(String elapsed_time, String total_distance, String date, String Id, String avgSpeed) {
        this.setElapsed_time(elapsed_time);
        this.setTotal_distance(total_distance);
        this.setDate(date);
        this.setId(Id);
        this.setAverage_speed(avgSpeed);


    }

    public String getAverage_speed() {
        return avgSpeed;
    }

    public void setAverage_speed(String average_speed) {
        this.avgSpeed = average_speed;
    }

    public String getId() {
        return Id;


    }

    public void setId(String id) {
        this.Id = id;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(String elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public String getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(String total_distance) {
        this.total_distance = total_distance;
    }
}

