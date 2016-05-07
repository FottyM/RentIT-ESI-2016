package com.rentit.maintenance.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(staticName = "of")
public class MaintenancePlan {
    @EmbeddedId
    MaintenancePlanID id;

    Integer yearOfAction;

    @ElementCollection
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="task_id"))})
    List<MaintenanceTaskID> tasks;
}
