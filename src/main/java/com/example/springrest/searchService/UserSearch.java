package com.example.springrest.searchService;

import com.example.springrest.model.Post;
import com.example.springrest.model.User;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class UserSearch {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> searchUserWithKeyword(String text){

        Query keywordQuery = getQueryBuilderForUsers()
                .keyword()
                .onField("name")
                .matching(text)
                .createQuery();

        List<User> results = getJpaQueryForUsers(keywordQuery).getResultList();

        return results;
    }

    public List<User> searchUserNameByFuzzyQuery(String text){

        Query fuzzyQuery = getQueryBuilderForUsers()
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(2)
                .withPrefixLength(0)
                .onFields("name")
                .matching(text +  "*")
                .createQuery();

        FullTextQuery jpaQueryForUsers = getJpaQueryForUsers(fuzzyQuery);
        return jpaQueryForUsers.getResultList();

    }

    public List<User> searchUserNameByFuzzyQueryBySort(String text){

        Sort sort = getQueryBuilderForUsers()
                .sort()
                .byField("sortName").asc()
                .createSort();

        Query fuzzyQuery = getQueryBuilderForUsers()
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(2)
                .withPrefixLength(0)
                .onFields("surname")
                .matching(text +  "*")
                .createQuery();

        FullTextQuery jpaQueryForUsers = getJpaQueryForUsers(fuzzyQuery).setSort(sort);
        return jpaQueryForUsers.getResultList();

    }

    public List<Post> findPostByUserName(String text){

        Query fuzzyQuery = getQueryBuilderForPosts()
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(2)
                .withPrefixLength(0)
                .onFields("user.name")
                .matching(text)
                .createQuery();

        List result = getJpaQueryForPosts(fuzzyQuery).getResultList();

        return result;
    }

    private FullTextQuery getJpaQueryForUsers(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(luceneQuery, User.class);
    }

    private FullTextQuery getJpaQueryForPosts(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(luceneQuery, Post.class);
    }

    private QueryBuilder getQueryBuilderForUsers() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(User.class)
                .get();
    }

    private QueryBuilder getQueryBuilderForPosts() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Post.class)
                .get();
    }

}
