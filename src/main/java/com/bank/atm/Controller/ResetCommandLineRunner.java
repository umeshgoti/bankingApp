package com.bank.atm.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class ResetCommandLineRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(ResetCommandLineRunner.class);
    private final ResetController resetController;

    @Autowired
    public ResetCommandLineRunner(ResetController resetController) {
        this.resetController = resetController;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Running reset all...");
        String res = resetController.resetAll();
        logger.info(res);
    }
}
