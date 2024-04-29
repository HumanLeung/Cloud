package org.company.controller;

import org.company.entity.Stage;
import org.company.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final StageRepository stageRepository;

    @Autowired
    public MonitorController(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @GetMapping("/check-is-ready")
    public boolean isRegister(@RequestParam("id") String id){
       Optional<Stage> stageOptional =  stageRepository.findByTId(id);
        return stageOptional.isPresent();
    }

    @GetMapping("/is-all-check")
    public boolean isAllCheck(@RequestParam("id") String id){
        Optional<Stage> stageOptional =  stageRepository.findByTId(id);
        if (stageOptional.isPresent()){
            Stage stage = stageOptional.get();
            return stage.getWaiterCount() == 0;
        }
      return false;
    }

    @GetMapping("/get-ready")
    public void getReady(@RequestParam("id") String id){
        Optional<Stage> stageOptional = stageRepository.findByTId(id);
        if (stageOptional.isPresent()){
           Stage stage = stageOptional.get();
           stage.setWaiterCount(stage.getWaiterCount() - 1);
           stageRepository.save(stage);
        }
    }

    @GetMapping("/complete")
    public void complete(@RequestParam("id") String id){
        Optional<Stage> stageOptional = stageRepository.findByTId(id);
        if (stageOptional.isPresent()){
            Stage stage = stageOptional.get();
            stage.setFinishCount(stage.getFinishCount() - 1);
            stageRepository.save(stage);
        }
    }
}
