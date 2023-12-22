package Assignment1PLC23WS;

import java.util.List;
import java.util.ArrayList;

public class ArticleManagement {
    private ArticleDAO articleDAO;

    public ArticleManagement(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<Article> getAllArticles() {
        return articleDAO.getArticleList();
    }

    public Article getArticle(int id) {
        return articleDAO.getArticle(id);
    }

    public void addArticle(Article article) {
        articleDAO.saveArticle(article);
    }

    public void deleteArticle(int id) {
        articleDAO.deleteArticle(id);
    }

    public int getTotalArticleCount() {
        return articleDAO.getArticleList().size();
    }

    public int getTotalBookCount() {
        List<Article> articles = articleDAO.getArticleList();
        return (int) articles.stream()
            .filter(article -> article instanceof Book)
            .count();
    }

    public int getTotalDVDCount() {
        List<Article> articles = articleDAO.getArticleList();
        return (int) articles.stream()
            .filter(article -> article instanceof DVD)
            .count();
    }

    public double getMeanPrice() {
        List<Article> articles = articleDAO.getArticleList();
        double totalPrice = articles.stream()
            .mapToDouble(Article::getPrice)
            .sum();
        return totalPrice / articles.size();
    }

    public List<Integer> getOldestArticleIds() {
        List<Article> articles = articleDAO.getArticleList();
        int oldestAge = Integer.MAX_VALUE;
        List<Integer> oldestIds = new ArrayList<>();

        for (Article article : articles) {
            int age = article.getAge();
            if (age < oldestAge) {
                oldestAge = age;
                oldestIds.clear();
                oldestIds.add(article.getId());
            } else if (age == oldestAge) {
                oldestIds.add(article.getId());
            }
        }

        return oldestIds;
    }
}

