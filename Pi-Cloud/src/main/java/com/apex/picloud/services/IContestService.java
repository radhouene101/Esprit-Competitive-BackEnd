package com.apex.picloud.services;

import com.apex.picloud.dtos.radhouene.ContestDto;
import com.apex.picloud.dtos.radhouene.ProjectsDto;

public interface IContestService extends AbstractService<ContestDto>{
    ContestDto customSaveContest(Long optionId ,ContestDto contestDto);
    ContestDto assignProjectToContest(Long contestDtoId , Long projectId);
    ContestDto updateContest(Long id, ContestDto contestDto,Long optionId);
    ContestDto unAssignProjectToContest(Long contestDtoId, Long projectId);
   // ProjectsDto setProjectWinnerByContest(Long contestID);
    ProjectsDto getProjectWinnerByContest(Long contestID);
}
