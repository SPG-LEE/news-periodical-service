package sq.news.periodical.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sq.base.ServiceResult;
import sq.constans.RestConstans;
import sq.news.periodical.entity.HotZone;
import sq.news.periodical.entity.PeriodicalEdition;
import sq.news.periodical.respository.EditionRepository;
import sq.news.periodical.respository.HotZoneRepository;
import sq.news.periodical.service.EditionService;
import sq.util.FormatUtil;
import sq.util.ServiceResultBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EditionHibernateService implements EditionService {
    @Autowired
    private EditionRepository editionRepository;
    @Autowired
    private HotZoneRepository hotZoneRepository;

    @Override
    public PeriodicalEdition findById(long id) {
        if (editionRepository.findById(id).isPresent()) {
            PeriodicalEdition result = editionRepository.findById(id).get();
            result.setHotZones(hotZoneRepository.findByEditionId(id));
            return result;
        }
        return null;
    }

    @Override
    public ServiceResult<List<PeriodicalEdition>> findAll(int pageSize, int pageNum, String title) {
        Page<PeriodicalEdition> result;
        if (!FormatUtil.isNullOrEmpty(title)) {
            result = editionRepository.findByTitleLike(title, new PageRequest(pageNum, pageSize,new Sort(Sort.Direction.DESC,"updateDate")));
        } else {
            result = editionRepository.findAll(new PageRequest(pageNum, pageSize,new Sort(Sort.Direction.DESC,"updateDate")));
        }
        return ServiceResultBuilder.buildSuccessMessageResult(result.getContent(), RestConstans.FIND_SUCCESS.getName(), result.getTotalElements());
    }

    @Override
    public void update(PeriodicalEdition entity) {
        entity.setUpdateDate(new Date());
        List<HotZone> oldHotZones = hotZoneRepository.findByEditionId(entity.getId());
        List<Long> oldIdList = oldHotZones.stream().map(HotZone::getId).collect(Collectors.toList());
        List<Long> newIdList = entity.getHotZones().stream().map(HotZone::getId).collect(Collectors.toList());
        oldIdList.removeAll(newIdList);
        oldIdList.stream().forEach(newId->{
            hotZoneRepository.deleteById(newId);
        });
       List<HotZone> needMerges = new ArrayList<>();
        if (entity.getHotZones().size()>0){
            entity.getHotZones().stream().forEach(hotZone->{
                Optional<HotZone> findHotZone = hotZoneRepository.findById(hotZone.getId());
                if (findHotZone!=null&&findHotZone.isPresent()){
                    HotZone needMerge = findHotZone.get();
                    needMerge.copyFrom(hotZone);
                    needMerges.add(needMerge);
                }
            });
            hotZoneRepository.saveAll(needMerges);
        }
        editionRepository.saveAndFlush(entity);
    }

    @Override
    public void save(PeriodicalEdition entity) {
        editionRepository.saveAndFlush(entity);
        if (entity.getHotZones().size()>0){
            entity.getHotZones().stream().forEach(hotZone->{
                hotZone.setEditionId(entity.getId());
            });
            hotZoneRepository.saveAll(entity.getHotZones());
        }
    }

    @Override
    public void delete(long id) {
        editionRepository.delete(editionRepository.findById(id).get());
    }

    @Override
    public List<PeriodicalEdition> findByPeriodicalId(Long id) {
        List<PeriodicalEdition> result = editionRepository.findByPeriodicalId(id);
        result.stream().forEach(edition->{
            edition.setHotZones(hotZoneRepository.findByEditionId(edition.getId()));
        });
        return result;
    }

}
