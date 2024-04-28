package com.apex.picloud.services;

import com.apex.picloud.dtos.radhouene.ContestDto;
public interface IContestService extends AbstractService<ContestDto>{
    ContestDto customSaveContest(Long optionId ,ContestDto contestDto);
    ContestDto assignProjectToContest(Long contestDtoId , Long projectId);
}
