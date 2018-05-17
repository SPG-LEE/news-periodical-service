package sq.news.periodical.service.hibernate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import sq.base.ServiceResult;
import sq.constans.RestConstans;
import sq.news.periodical.entity.Periodical;
import sq.news.periodical.respository.PeriodicalRepository;
import sq.news.periodical.service.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sq.util.FormatUtil;
import sq.util.ServiceResultBuilder;

import java.util.Date;
import java.util.List;

@Service
public class PeriodicalHibernateService implements PeriodicalService {
    @Autowired
    private PeriodicalRepository periodicalRepository;

    @Override
    public Periodical findById(long id) {
        if (periodicalRepository.findById(id).isPresent()) {
            return periodicalRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public ServiceResult<List<Periodical>> findAll(int pageSize, int pageNum, String title) {
        Page<Periodical> result;
        if (!FormatUtil.isNullOrEmpty(title)) {
            result = periodicalRepository.findByTitleLike(title, new PageRequest(pageNum, pageSize,new Sort(Sort.Direction.DESC,"updateDate")));
        } else {
            result = periodicalRepository.findAll(new PageRequest(pageNum, pageSize,new Sort(Sort.Direction.DESC,"updateDate")));
        }
        return ServiceResultBuilder.buildSuccessMessageResult(result.getContent(), RestConstans.FIND_SUCCESS.getName(), result.getTotalElements());
    }

    @Override
    public void save(Periodical entity) {
        periodicalRepository.saveAndFlush(entity);
    }

    @Override
    public void update(Periodical entity) {
        entity.setUpdateDate(new Date());
        periodicalRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(long id) {
        periodicalRepository.deleteById(id);
    }

}
