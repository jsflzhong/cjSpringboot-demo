package com.cj.camel.router;

import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 文件复制的Router
 */
@Component
public class FileCopyRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(FileCopyRouter.class);

    /**
     * Untested
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@FileCopyRouter is running...");

        from("file:/home/cuijian/test/test1.txt?delay=3000")
                .to("file:/home/cuijian/test2/test2.txt");

        logger.info("@@@FileCopyRouter is finishing...");
    }
}
