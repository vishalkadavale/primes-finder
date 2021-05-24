package com.ubsync.primes.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class PrimesGeneratorTests {

    @Autowired
    private PrimesGenerator primesGenerator;

    @Value("${primes.upper.limit:1000}")
    private int uppperLimit;

    @Test
    public void getPrimesUptoLruTest(){
        Assertions.assertTrue(primesGenerator.getPrimesUptoLru(0).isEmpty());
        Assertions.assertTrue(primesGenerator.getPrimesUptoLru(1).isEmpty());
        Assertions.assertEquals(Arrays.asList(2),  primesGenerator.getPrimesUptoLru(2));
        Assertions.assertTrue(primesGenerator.getPrimesUptoLru(uppperLimit+1).isEmpty());
    }


}
