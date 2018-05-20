package sq.news.periodical.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sq.base.ServiceResult;
import sq.constans.RestConstans;
import sq.news.periodical.entity.Article;
import sq.news.periodical.entity.ArticleComment;
import sq.news.periodical.entity.Periodical;
import sq.news.periodical.entity.PeriodicalEdition;
import sq.news.periodical.respository.ArticleCommentRepository;
import sq.news.periodical.respository.ArticleRepository;
import sq.news.periodical.respository.EditionRepository;
import sq.news.periodical.respository.PeriodicalRepository;
import sq.news.periodical.service.ArticleService;
import sq.util.FormatUtil;
import sq.util.ServiceResultBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleHibernateService implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private PeriodicalRepository periodicalRepository;
    @Autowired
    private EditionRepository editionRepository;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Override
    public Article findByIdAndHasAudit(long id) {
        return articleRepository.findByIdAndHasAudit(id, true);
    }
    @Override
    public Article findById(long id) {
        if (articleRepository.findById(id).isPresent()) {
            return articleRepository.findById(id).get();
        }
        return null;
    }
    @Override
    public ServiceResult<List<Article>> findAll(int pageSize, int pageNum, String title) {
        Page<Article> result;
        if (!FormatUtil.isNullOrEmpty(title)) {
            result = articleRepository.findByTitleLike(title, new PageRequest(pageNum, pageSize, new Sort(Sort.Direction.DESC, "updateDate")));
        } else {
            result = articleRepository.findAll(new PageRequest(pageNum, pageSize, new Sort(Sort.Direction.DESC, "updateDate")));
        }
        return ServiceResultBuilder.buildSuccessMessageResult(result.getContent(), RestConstans.FIND_SUCCESS.getName(), result.getTotalElements());
    }

    @Override
    public void save(Article entity) {
        if (entity.getEditionId() != 0) {
            Optional<Periodical> findResult = periodicalRepository.findById(entity.getPeriodicalId());
            if (findResult != null && findResult.get() != null) {
                entity.setPeriodical(findResult.get().getTitle());
            }
        }
        if (entity.getPeriodicalId() != 0) {
            Optional<PeriodicalEdition> findResult = editionRepository.findById(entity.getEditionId());
            if (findResult != null && findResult.get() != null) {
                entity.setEditionPeriod(findResult.get().getTitle());
            }
        }
        articleRepository.saveAndFlush(entity);
    }

    @Override
    public void update(Article entity) {
        entity.setUpdateDate(new Date());
        save(entity);
    }

    @Override
    public List<ArticleComment> findComments(long articleId) {
        return articleCommentRepository.findByArticleIdAndAndHasShow(articleId, true,new Sort(Sort.Direction.DESC,"updateDate"));
    }

    @Override
    public void delete(long id) {
        articleRepository.delete(articleRepository.findById(id).get());
    }

    @Override
    public void saveComment(ArticleComment comment) {
        articleCommentRepository.saveAndFlush(comment);
    }

    @Override
    public void updateAll(List<Article> needUpdate) {
        articleRepository.saveAll(needUpdate);
    }

    @Override
    public Article findByIdAndHasShow(long id) {
        return articleRepository.findByIdAndHasShowAndHasAudit(id, true, true);
    }

    @Override
    public List<Article> findByEditionId(long id) {
        return articleRepository.findByEditionId(id);
    }

}
