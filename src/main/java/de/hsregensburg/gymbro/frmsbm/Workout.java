package de.hsregensburg.gymbro.frmsbm;

public class Workout {

    private int start_time;
    private int end_time;
    private String weekday;
    private String title;

    public Workout(String t) {
        this.title = t;
    }

    public Workout(int s, int e, String w, String t) {
        this.start_time = s;
        this.end_time = e;
        this.weekday = w;
        this.title = t;
    }

    public void update_Workout(int s, int e, String w, String t) {
        this.start_time = s;
        this.end_time = e;
        this.weekday = w;
        this.title = t;
    }

    public int getStart_time() {
        return start_time;
    }
    public int getEnd_time() {
        return end_time;
    }

    public void setStart_time(int s) {
        this.start_time = s;
    }
    public void setEnd_time(int e) {
        this.end_time = e;
    }

    public String getWeekday() { return weekday; }
    public String getTitle() {
        return title;
    }

    public void setTitle(String content) {
        this.title = content;
    }
    public void setWeekday(String weekday) { this.weekday = weekday; }
}
