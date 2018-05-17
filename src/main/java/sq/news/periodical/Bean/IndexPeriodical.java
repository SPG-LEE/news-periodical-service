package sq.news.periodical.Bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import sq.news.periodical.entity.Periodical;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class IndexPeriodical {
    private Periodical oldPeriodical;
    private Periodical newPeriodical;
}
