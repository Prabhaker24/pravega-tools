/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.tools.pravegacli.commands.bookkeeper;

import io.pravega.segmentstore.storage.impl.bookkeeper.DebugLogWrapper;
import io.pravega.tools.pravegacli.commands.CommandArgs;
import lombok.Cleanup;
import lombok.val;

/**
 * Lists all BookKeeper Logs.
 */
public class BookKeeperListCommand extends BookKeeperCommand {
    /**
     * Creates a new instance of the BookKeeperListCommand.
     * @param args The arguments for the command.
     */
    public BookKeeperListCommand(CommandArgs args) {
        super(args);
    }

    @Override
    public void execute() throws Exception {
        ensureArgCount(0);

        // Loop through all known log ids and fetch their metadata.
        @Cleanup
        val context = createContext();
        for (int logId = 0; logId < context.serviceConfig.getContainerCount(); logId++) {
            @Cleanup
            DebugLogWrapper log = context.logFactory.createDebugLogWrapper(logId);
            val m = log.fetchMetadata();
            outputLogSummary(logId, m);
        }
    }

    public static CommandDescriptor descriptor() {
        return new CommandDescriptor(BookKeeperCommand.COMPONENT, "list", "Lists all BookKeeper Logs.");
    }
}
