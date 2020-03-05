package PremierLeague.Fantasy.model.appLogic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class League {

    @Id
    private Long id;

    private String name;
    private String created;
    private boolean closed;
    private Long rank;
    private Long max_entries;
    private String league_type;
    private String scoring;

    private Long admin_entry;

    private Integer starting_event;
    private String copy_privacy;


}
