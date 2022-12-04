package de.othr.im.gymbro.frmsbm;

import java.util.Objects;

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

    public Workout[] getWorkouts() { return this.workouts;}
    public void addWorkout(Workout w) {
        int verify_result = this.verifyPlan(w);
        if(verify_result != 0) {
            System.out.println("Couldn't add Workout as it conflicts with Workout " + Integer.toString(verify_result));
            return;
        }
        verify_result = this.verifyWorkout(w);
        if(verify_result == 1) {
            System.out.println("Couldn't add Workout as it has no time range: " + Integer.toString(w.getStart_time()) +
                                " - " + Integer.toString(w.getEnd_time()));
            return;
        }
        int n = this.workouts.length;
        Workout[] new_array = new Workout[n+1];
        for (int i = 0; i < n; i++)
            new_array[i] = workouts[i];

        new_array[n] = w;
        this.workouts = new_array;
    }

    public int verifyPlan(Workout w) {
        int result = 1;
        // first element is skipped because its the header element
        for(int i = 1; i < this.workouts.length; i++) {
            result = i;
            Workout check_workout = this.workouts[i];
            // critical zone
            if (Objects.equals(check_workout.getWeekday(), w.getWeekday())) {
                // can't have same title on same day
                if (Objects.equals(check_workout.getTitle(), w.getTitle()))
                    return result;
                if (check_workout.getStart_time() >= w.getEnd_time()) {
                    continue;
                } else if (check_workout.getEnd_time() <= w.getStart_time()) {
                    continue;
                } else {
                    // workout is in between another workout
                    return result;
                }
            }
        }
        return 0;
    }
    public int verifyWorkout(Workout w) {
        if(w.getStart_time() >= w.getEnd_time()) {
            return 1;
        } else {
            return 0;
        }
    }

    public void deleteWorkout(String workout_title) {
        int tmp = 0;
        int n = this.workouts.length;
        for(int i = 0; i<n; i++) {
            if (Objects.equals(this.workouts[i].getTitle(), workout_title)) {
                tmp = i;
            }
        }
        if(tmp == 0) {
            System.out.println("Couldnt find requested Workout!");
            return;
        }
        Workout[] new_array = new Workout[n-1];
        for (int i = 0, k = 0; i < n; i++) {
            if(i == tmp)
                continue;
            else
                new_array[k++] = workouts[i];
        }
        this.workouts = new_array;
    }
    public int updateWorkoutPlan(String old_title, int s, int e, String w, String t) {
        int result = 1;
        for(Workout workout : this.workouts) {
            // it makes 0 sense this way, only updates first entry, which doesnt have to be the right one
            // solution: create ID, but how global?
            if (Objects.equals(workout.getTitle(), old_title)) {
                workout.update_Workout(s, e, w, t);
                result = 0;
                break;
            }
        }
        return result;
    }
    public Workout getWorkoutFromName(String name) {
        for (Workout workout : this.workouts) {
            if (Objects.equals(workout.getTitle(), name)) {
                return workout;
            }
        }
        System.out.println("Could not find workout with given name.");
        return null;
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


