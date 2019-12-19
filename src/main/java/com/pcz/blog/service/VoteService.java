package com.pcz.blog.service;

import com.pcz.blog.domain.Vote;

/**
 * @author picongzhi
 */
public interface VoteService {
    /**
     * 根据id获取Vote
     *
     * @param id id
     * @return Vote
     */
    Vote getVoteById(Long id);

    /**
     * 删除Vote
     *
     * @param id id
     */
    void removeVote(Long id);
}
