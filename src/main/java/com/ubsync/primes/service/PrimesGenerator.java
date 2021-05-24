package com.ubsync.primes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentLruCache;

import java.util.*;

/**
 * This service generates the primes number up to the provided number
 */
@Service
public class PrimesGenerator {

    @Value("${primes.upper.limit:1000}")
    private int upperLimit;

    private LinkedList<Integer> primes = new LinkedList<>(Arrays.asList(2,3,5,7,11,13,17,19,23,29));
    private ConcurrentLruCache<Integer, Integer> lruCache = new ConcurrentLruCache<>(100, this::getIndexFor);

    /**
     *  It returns the sublist containing the all primes number up to an including the number provided
     *  if numbers up to the provided number are not present in list it calls other method to find those.
     * @param limit
     * @return sublist containing prime numbers
     */
    public List<Integer> getPrimesUptoLru(int limit) {
        //no prime numbers below 2
        if(limit < 2 || limit > upperLimit )
            return Collections.emptyList();

        //check if already primes checked upto this limit and index was cached
        Integer cachedIndex = lruCache.get(limit);
        return primes.subList(0,cachedIndex);
    }

    /**
     * Checks if given number is prime number
     * @param numToCheck
     * @return true if number is prime
     */
    private static boolean isPrime(int numToCheck) {
        Double sqrt = Math.sqrt(numToCheck);
        for(int i=2; i < sqrt.intValue()+1; i++){
            if(numToCheck % i==0)
                return false;
        }
        return true;
    }

    /**
     * It finds the primes number up to provided number if those are not in already found list and returns the list
     * @param limit limit up to which prime number list to be extended
     */
    private synchronized void getExtendedPrimesListUpto(int limit){
        int nextToCheck = primes.get(primes.size()-1) + 2;
        while(primes.getLast() < limit){
            if(isPrime(nextToCheck)){
                primes.add(nextToCheck);
            }
            nextToCheck += 2;
        }

    }

    public int getCacheSize(){
        return lruCache.size();
    }

    public int primeListSize(){
        return primes.size();
    }

    public int maxTillFound(){
        return primes.getLast();
    }

    /**
     * Finds index of prime number in primes list which is greater than or equal to the limit
     * @param limit number for which index to be found
     * @return index of primes list such that primes number in the list are equal to or less than the limit
     */
    private int getIndexFor(int limit){
        int i=0;
        for(; i < primes.size() ; i++){
            if(primes.get(i) >= limit)
                break;
        }
        if( i < primes.size()) {
            return primes.get(i)==limit ? i+1 : i;
        }
        else {
            getExtendedPrimesListUpto(limit);
            return getIndexFor(limit);
        }
    }
}
