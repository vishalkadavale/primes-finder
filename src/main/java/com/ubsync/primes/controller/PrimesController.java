package com.ubsync.primes.controller;

import com.ubsync.primes.service.PrimesGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PrimesController {

    private static Logger logger = LoggerFactory.getLogger(PrimesController.class);

    @Value("${primes.upper.limit:1000}")
    private int upperLimit;

    @Autowired
    private PrimesGenerator primesGenerator;

    @GetMapping("/primes/{num}")
    public Map<String, Object> getPrimesUpto(@PathVariable int num){
        Map<String, Object> map = new HashMap<>();
       map.put("initial", num);
       if( num < upperLimit)
           map.put("Primes", primesGenerator.getPrimesUptoLru(num));
       else
           map.put("Primes","Number is too high to find primes up to, not supporting at this moment!");
        return map;
    }

    @GetMapping("/cache/size")
    public int getCacheSize(){
        return primesGenerator.getCacheSize();
    }

    @GetMapping("/max")
    public int getMaxPrimeFoundYet(){
        return primesGenerator.maxTillFound();
    }

    @GetMapping("/size")
    public int getPrimesSize(){
       return primesGenerator.primeListSize();
    }
}
