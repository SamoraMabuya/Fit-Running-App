package mobile;

public class RetrieveRunnerActivity {

    private String elapsed_time;
    private String total_distance;
    private String date;

    public RetrieveRunnerActivity(String elapsed_time, String total_distance, String date) {
        this.setElapsed_time(elapsed_time);
        this.setTotal_distance(total_distance);
        this.setDate(date);

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

