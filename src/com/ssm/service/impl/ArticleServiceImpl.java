package com.ssm.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssm.common.BasicServiceImpl;
import com.ssm.entity.Article;
import com.ssm.mapper.ArticleMapper;
import com.ssm.service.ArticleService;

@Service("articleService")
public class ArticleServiceImpl extends BasicServiceImpl<Article, Long, ArticleMapper> implements ArticleService{
	@Autowired
	private ArticleMapper articleMapper;

	@Override
	protected ArticleMapper getMapper() {
		return articleMapper;
	}
}