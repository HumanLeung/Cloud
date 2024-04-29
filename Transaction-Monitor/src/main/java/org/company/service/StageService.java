package org.company.service;

import org.company.entity.Stage;
import org.company.repository.StageRepository;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class StageService {
  private final StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public void saveRecord(Stage stage){
     stageRepository.save(stage);
 }

}
