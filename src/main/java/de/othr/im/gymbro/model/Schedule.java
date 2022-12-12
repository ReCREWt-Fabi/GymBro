package de.othr.im.gymbro.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date pauseFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date pauseTo;

    public Date getPauseFrom() {
        return pauseFrom;
    }

    public void setPauseFrom(Date pauseFrom) {
        this.pauseFrom = pauseFrom;
    }

    public Date getPauseTo() {
        return pauseTo;
    }

    public void setPauseTo(Date pauseTo) {
        this.pauseTo = pauseTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPauseScheduled() {
        final Date now = new Date();
        return pauseFrom != null && pauseTo != null && now.before(pauseFrom);
    }

    public boolean isPaused() {
        final Date now = new Date();
        return pauseFrom != null && pauseTo != null && now.after(pauseFrom) && now.before(pauseTo);
    }
}