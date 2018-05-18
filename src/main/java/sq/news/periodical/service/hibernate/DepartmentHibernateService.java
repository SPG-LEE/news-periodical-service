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
import sq.news.periodical.entity.Department;
import sq.news.periodical.respository.ArticleCommentRepository;
import sq.news.periodical.respository.ArticleRepository;
import sq.news.periodical.respository.DepartmentRepository;
import sq.news.periodical.service.ArticleService;
import sq.news.periodical.service.DepartmentService;
import sq.util.FormatUtil;
import sq.util.ServiceResultBuilder;

import java.util.Date;
import java.util.List;

@Service
public class DepartmentHibernateService implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public ServiceResult<List<Department>> findAll(int pageSize, int pageNum) {
        Page<Department> result = departmentRepository.findAll(new PageRequest(pageNum, pageSize));
        return ServiceResultBuilder.buildSuccessMessageResult(result.getContent(),RestConstans.FIND_SUCCESS.getName(),result.getTotalElements());
    }
}
