package myproject.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author paveldikin
 * @date 27.06.2020
 */
@ToString
@Entity
@Getter
@Table(name = "queue_log")
public class Сongestion {
    @Id
    private Long id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic
    @Temporal(TemporalType.TIME)
    @Column(name = "start_time_of_wait")
    private Date startTime;
    @Basic
    @Temporal(TemporalType.TIME)
    @Column(name = "end_time_of_wait")
    private Date finishTime;
    @Basic
    @Temporal(TemporalType.TIME)
    @Column(name = "end_time_of_service")
    private Date finishService;
    @Column(name = "branches_id")
    private Long branchesId;
}
