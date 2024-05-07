package com.apex.picloud.services;

import com.apex.picloud.dtos.radhouene.ContestDto;
import com.apex.picloud.dtos.radhouene.ProjectsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IContestService extends AbstractService<ContestDto>{
    ContestDto customSaveContest(Long optionId ,ContestDto contestDto);
    ContestDto assignProjectToContest(Long contestDtoId , Long projectId);
    ContestDto updateContest(Long id, ContestDto contestDto,Long optionId);
    ContestDto unAssignProjectToContest(Long contestDtoId, Long projectId);
   // ProjectsDto setProjectWinnerByContest(Long contestID);
    ProjectsDto getProjectWinnerByContest(Long contestID);
    void uploadContestImage(Long contestId, MultipartFile file) throws IOException;
}
