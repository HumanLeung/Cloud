package com.example.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 */

@Component
@FeignClient(value = "CLOUD-MONITOR-SERVICE")
public interface TransactionService {

    /**
     * a
     * @param id
     * @return
     */
    @GetMapping(value = "/monitor/check-is-ready")
    public boolean isRegister(@RequestParam("id") String id);
    /**
     * @param id
     * @return
     */
    @GetMapping("/monitor/is-all-check")
    public boolean isAllCheck(@RequestParam("id") String id);

    @GetMapping("/monitor/get-ready")
    public void getReady(@RequestParam("id") String id);

    @GetMapping("/monitor/complete")
    public void complete(@RequestParam("id") String id);


}
