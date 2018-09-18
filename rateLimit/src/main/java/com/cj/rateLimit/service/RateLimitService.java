package com.cj.rateLimit.service;

/**
 * Created by Jian.Cui on 2018/8/30.
 */
public interface RateLimitService {
    boolean tryAcquire();
}
