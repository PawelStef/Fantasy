package PremierLeague.Fantasy.model.appLogic.teamHistory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Current {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long event;
    private long points;
    private long total_points;
    private long rank;
    private long rank_sort;
    private long overall_rank;
    private long bank;
    private long value;
    private long event_transfers;
    private long event_transfers_cost;
    private long points_on_bench;


    @ManyToOne(fetch = FetchType.LAZY)
    private TeamHistory teamHistory;
}
