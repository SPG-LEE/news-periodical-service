package sq.news.periodical.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sq.base.ServiceResult;
import sq.constans.RestConstans;
import sq.news.periodical.entity.Article;
import sq.news.periodical.respository.ArticleRepository;
import sq.news.periodical.service.ArticleService;
import sq.util.FormatUtil;
import sq.util.ServiceResultBuilder;

import java.util.Date;
import java.util.List;

@Service
public class ArticleHibernateService implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

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
            result = articleRepository.findByTitleLike(title, new PageRequest(pageNum, pageSize,new Sort(Sort.Direction.DESC,"updateDate")));
        } else {
            result = articleRepository.findAll(new PageRequest(pageNum, pageSize,new Sort(Sort.Direction.DESC,"updateDate")));
        }
        return ServiceResultBuilder.buildSuccessMessageResult(result.getContent(), RestConstans.FIND_SUCCESS.getName(), result.getTotalElements());
    }

    @Override
    public void save(Article entity) {
        articleRepository.saveAndFlush(entity);
    }

    @Override
    public void update(Article entity) {
        entity.setUpdateDate(new Date());
        articleRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(long id) {
        articleRepository.deleteById(id);
    }

}
