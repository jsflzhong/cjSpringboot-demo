package com.cj.eventBus.subscriber;

import com.cj.eventBus.utils.YmlUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jsflz on 2018/8/13.
 * The first subscriber
 *
 * @author cj
 */
public class MonitorCounterSubscriber implements Subscriber {

    private static final Logger logger = LoggerFactory.getLogger(MonitorCounterSubscriber.class);

    //Key: Get seal's id failed when sign.
    public static final String KEY_SIGN_GET_ID_FAILED = "SIGN_GET_ID_FAILED";
    //CounterPool to hold different kind of counters.
    private static final Map<String, AtomicInteger> counterPool = new HashMap<>();

    //The map of alarm
    private static Map<String, Pair<String, String>> alarm = ImmutableMap.of(
            KEY_SIGN_GET_ID_FAILED, new ImmutablePair<>(
                    YmlUtils.getAlarm().get("profix"), YmlUtils.getAlarm().get("lf_url")));


    /**
     * Run the logistic of monitor asynchronously with ConcurrentLinkedQueue defined in Guava.
     *
     * @author cj
     */
    @Subscribe
    //@AllowConcurrentEvents //Open this annotation when the Thread-safe is needed.
    public void doMonitor(String stringEvent) {
        logger.info("@@@First subscriber got the event:{}",stringEvent);

        //business code
        /*try {
            Integer wrongNum = increaseCounter(stringEvent, 1);
            handleFetchSealAlarm(stringEvent,wrongNum);
        } catch (Exception e) {
            logger.error("发送[获取签章失败]的报警异常:{}", e);
        }*/
    }

    /**
     * Add one counter then return the total counter in the counter_pool.
     *
     * @param key   Key in the counterPool
     * @param delta delta
     * @author cj
     * @deprecated (Business code)
     */
    @Deprecated
    private Integer increaseCounter(String key, int delta) {
        if (!counterPool.containsKey(key)) {
            synchronized (this) {
                if (!counterPool.containsKey(key)) {
                    counterPool.put(key, new AtomicInteger(0));
                }
            }
        }
        return counterPool.get(key).addAndGet(delta);
    }

    /**
     * Handle the alarm of fetching the seal in sign.
     *
     * @param wrongNum The counter of fetching the seal in sign in the counter pool.
     * @author cj
     * @deprecated (Business code)
     */
    @Deprecated
    private void handleFetchSealAlarm(String stringEvent, Integer wrongNum) {
        //Business code.
        /*Exia.of(wrongNum).takeApply(num->num>100,o -> {
            String alarmProfix = alarm.get(stringEvent).getLeft();
            String alarmUrl = alarm.get(stringEvent).getRight();
            String alarmStr = "[" + alarmProfix + "]签章系统获取电子章异常数达到100次！";
            ImmutableMap<String, Object> postMap = ImmutableMap.<String, Object>builder().
                    put("outPut", alarmStr).build();
            String alarmRes = HttpUtils.restPost(alarmUrl, postMap);
            if (org.apache.commons.lang.StringUtils.startsWith(alarmRes, "success")) {
                logger.info("报警发送成功");
                clear(KEY_SIGN_GET_ID_FAILED);
            } else {
                logger.info("报警发送失败");
            }
         });*/
    }

    private static void clear(String key) {
        counterPool.put(key, new AtomicInteger(0));
    }


}
