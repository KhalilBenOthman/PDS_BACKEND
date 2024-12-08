package com.spsrh.userService.service;

import com.spsrh.userService.model.ManagerTeam;
import java.util.List;
import java.util.Optional;

public interface ManagerTeamService {
    ManagerTeam createManagerTeam(ManagerTeam managerTeam);
    Optional<ManagerTeam> getManagerTeamById(Long teamId);
    List<ManagerTeam> getAllManagerTeams();
    ManagerTeam updateManagerTeam(ManagerTeam managerTeam);
    void deleteManagerTeam(Long teamId);
}
