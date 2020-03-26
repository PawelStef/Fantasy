package PremierLeague.Fantasy.model.appLogic.teamHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "teamHistory", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<Current> current;

    //ArrayList <Past> past = new ArrayList <Past> ();

    //ArrayList<Chips> chips = new ArrayList<Chips>();
}
