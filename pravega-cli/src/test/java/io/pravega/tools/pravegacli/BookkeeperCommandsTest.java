/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.tools.pravegacli;

import io.pravega.tools.pravegacli.commands.AdminCommandState;
import java.util.Properties;
import org.apache.bookkeeper.test.BookKeeperClusterTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test basic functionality of Bookkeeper commands.
 */
public class BookkeeperCommandsTest extends BookKeeperClusterTestCase {

    private static AdminCommandState STATE;

    public BookkeeperCommandsTest() {
        super(5);
    }

    @Before
    public void setUp() throws Exception {
        baseConf.setLedgerManagerFactoryClassName("org.apache.bookkeeper.meta.FlatLedgerManagerFactory");
        baseClientConf.setLedgerManagerFactoryClassName("org.apache.bookkeeper.meta.FlatLedgerManagerFactory");
        super.setUp();

        STATE = new AdminCommandState();
        Properties bkProperties = new Properties();
        bkProperties.setProperty("pravegaservice.containerCount", "4");
        bkProperties.setProperty("pravegaservice.zkURL", zkUtil.getZooKeeperConnectString());
        bkProperties.setProperty("bookkeeper.bkLedgerPath", "/ledgers");
        STATE.getConfigBuilder().include(bkProperties);
    }


    @Test
    public void testBookKeeperListCommand() throws Exception {
        String commandResult = TestUtils.executeCommand("bk list", STATE);
        Assert.assertTrue(commandResult.contains("Log 0"));
    }

    @Test
    public void testBookKeeperDetailsCommand() throws Exception {
        String commandResult = TestUtils.executeCommand("bk details ", STATE);
        Assert.assertTrue(commandResult.contains("Ledger"));
    }
}