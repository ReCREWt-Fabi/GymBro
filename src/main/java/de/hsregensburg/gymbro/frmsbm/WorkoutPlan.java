package de.hsregensburg.gymbro.frmsbm;

public class WorkoutPlan {

    private String title;
    private Workout[] workouts;
    Workout header = new Workout("Header Element");

    public WorkoutPlan() {
        this.workouts = new Workout[1];
        this.workouts[0] = this.header;
    }

    public WorkoutPlan(String t) {
        workouts = new Workout[1];
        this.title = t;
        this.workouts[0] = this.header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addWorkout(Workout w) {
        int n = this.workouts.length;
        Workout[] new_array = new Workout[n+1];
        for (int i = 0; i < n; i++)
            new_array[i] = workouts[i];

        new_array[n] = w;
        this.workouts = new_array;
    }

    public void fillPlanUsingTemplate(int id) {
        if(id == 0001) this.fillPlanUsingLegTemplate();
        if(id == 0002) this.fillPlanUsingChestTemplate();
        if(id == 0003) this.fillPlanUsingBackTemplate();
    }

    private void fillPlanUsingLegTemplate() {
        Workout a = new Workout(1200, 1300, "Mon", "Leg Workout 1");
        Workout b = new Workout(1200, 1300, "Wed", "Leg Workout 2");
        Workout c = new Workout(1200, 1300, "Fri", "Leg Workout 3");
        this.addWorkout(a);
        this.addWorkout(b);
        this.addWorkout(c);
    }

    private void fillPlanUsingChestTemplate() {
        Workout a = new Workout(1500, 1600, "Tue", "Chest Workout 1");
        Workout b = new Workout(1500, 1600, "Thu", "Chest Workout 2");
        Workout c = new Workout(1500, 1600, "Sat", "Chest Workout 3");
        this.addWorkout(a);
        this.addWorkout(b);
        this.addWorkout(c);
    }

    private void fillPlanUsingBackTemplate() {
        Workout a = new Workout(1500, 1600, "Mon", "Back Workout 1");
        Workout b = new Workout(1500, 1600, "Thu", "Back Workout 2");
        Workout c = new Workout(1500, 1600, "Sun", "Back Workout 3");
        this.addWorkout(a);
        this.addWorkout(b);
        this.addWorkout(c);
    }

    public void printWorkouts() {
        System.out.println(this.title + ":");
        for (int i = 0; i < this.workouts.length; i++) {
            System.out.println(this.workouts[i].getTitle());
        }
    }

}


