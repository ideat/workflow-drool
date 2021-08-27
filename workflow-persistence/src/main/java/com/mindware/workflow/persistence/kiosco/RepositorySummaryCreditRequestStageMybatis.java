package com.mindware.workflow.persistence.kiosco;

import com.mindware.workflow.core.entity.kiosco.SummaryCreditRequestStage;
import com.mindware.workflow.core.service.data.kiosco.RepositorySummaryCreditRequestStage;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositorySummaryCreditRequestStageMybatis implements RepositorySummaryCreditRequestStage {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperSummaryCreditRequestStage mapper;

    RepositorySummaryCreditRequestStageMybatis(){}

    public static RepositorySummaryCreditRequestStage create(SqlSessionFactory sqlSessionFactory){
        RepositorySummaryCreditRequestStageMybatis repository = new RepositorySummaryCreditRequestStageMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<SummaryCreditRequestStage> findActiveRequestByIdCard(String idCard) {
        return mapper.findActiveRequestByIdCard(idCard);
    }
}
