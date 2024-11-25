package com.matchingMatch.admin.service;

import com.matchingMatch.listener.TeamDeleteEvent;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.team.domain.entity.TeamEntity;
import com.matchingMatch.user.domain.UserDetail;
import com.matchingMatch.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {


    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void banUser(Long id, Integer day) {
        UserDetail userDetail = userRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);

        userDetail.ban(day);

    }

    @Transactional
    public void unBanUser(Long id) {
        UserDetail userDetail = userRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);

        userDetail.unban();

    }

    @Transactional
    public void withDrawUser(Long id) {

        userRepository.deleteById(id);
        TeamEntity team = teamRepository.findByLeaderId(id).orElse(null);

        if (team == null) {
            return;
        }
        teamRepository.delete(team);
        eventPublisher.publishEvent(new TeamDeleteEvent(team.getId()));

    }

    @Transactional
    public List<UserDetail> getUsers(Integer page, Integer size) {
        Page<UserDetail> users = userRepository.findAll(PageRequest.of(page, size));
        return users.getContent();
    }

}
