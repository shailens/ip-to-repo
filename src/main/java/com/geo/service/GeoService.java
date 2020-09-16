package com.geo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * user: shailendra
 * Date: 16/09/20
 * Time: 7:42 PM
 */
@Service("geoService")
public class GeoService {

    private static final String mmdbFileLocation = "/path/to/GeoLite2-City.mmdb";
    private ObjectMapper mapper = new ObjectMapper();

    public String getGeoLocationByIp(String ip) throws Exception{
        File database = new File(mmdbFileLocation);
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
    }
}