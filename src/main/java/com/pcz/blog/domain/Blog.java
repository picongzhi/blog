package com.pcz.blog.domain;

import com.github.rjeschke.txtmark.Processor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author picongzhi
 */
@Entity
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @NotEmpty(message = "摘要不能为空")
    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String summary;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "readings")
    private Integer readings = 0;

    @Column(name = "comments")
    private Integer comments = 0;

    @Column(name = "votes")
    private Integer votes = 0;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_comment",
            joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> commentList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "blog_vote",
            joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
    private List<Vote> voteList;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @Column(name = "tags", length = 100)
    private String tags;

    protected Blog() {
    }

    public Blog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.htmlContent = Processor.process(content);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getReadings() {
        return readings;
    }

    public void setReadings(Integer readings) {
        this.readings = readings;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        this.comments = this.commentList.size();
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
        comments = commentList.size();
    }

    public void removeComment(Long commentId) {
        for (int i = 0; i < commentList.size(); i++) {
            if (commentList.get(i).getId().equals(commentId)) {
                commentList.remove(i);
                break;
            }
        }

        comments = commentList.size();
    }

    public List<Vote> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }

    public boolean addVote(Vote vote) {
        boolean existed = false;
        for (int i = 0; i < voteList.size(); i++) {
            if (voteList.get(i).getUser().getId().equals(vote.getUser().getId())) {
                existed = true;
                break;
            }
        }

        if (!existed) {
            voteList.add(vote);
            votes = voteList.size();
        }

        return existed;
    }

    public void removeVote(Long voteId) {
        for (int i = 0; i < voteList.size(); i++) {
            if (voteList.get(i).getId().equals(voteId)) {
                voteList.remove(i);
                break;
            }
        }

        votes = voteList.size();
    }
}
