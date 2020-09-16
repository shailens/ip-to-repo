package com.geo.controller;

import com.geo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeoController {

    @Autowired
    private GeoService geoService;

    @GetMapping("/")
    public String hello(){
        return "Welcome to Ip to Geo Location Api";
    }

    @GetMapping("/ip-to-geo")
    public ResponseEntity<String> getLocation(@RequestParam("ip")String ip){
        ResponseEntity<String> responseEntity = null;
        try{
            if(!isValidIp(ip))throw new Exception("Invalid Ip Address :"+ip);
            String response = geoService.getGeoLocationByIp(ip);
            responseEntity = ResponseEntity.status(200).body(response);
        }catch (Exception e){
            responseEntity = ResponseEntity.status(500).body(e.getMessage());
        }
        return responseEntity;
    }

    private boolean isValidIp(String ip) {
        if (ip == null || ip.length() == 0)
            return false;
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        try {
            for (String p : parts) {
                int val = Integer.parseInt(p);
                if (val < 0 || val > 255) return false;
            }
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
