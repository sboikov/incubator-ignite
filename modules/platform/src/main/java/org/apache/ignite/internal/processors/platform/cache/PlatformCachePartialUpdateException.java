/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.platform.cache;

import org.apache.ignite.cache.*;
import org.apache.ignite.internal.portable.*;
import org.apache.ignite.internal.processors.platform.*;
import org.apache.ignite.internal.processors.platform.utils.*;

import java.util.*;

/**
 * Interop cache partial update exception.
 */
public class PlatformCachePartialUpdateException extends PlatformException implements PlatformExtendedException {
    /** */
    private static final long serialVersionUID = 0L;

    /** Platform context. */
    private final PlatformContext ctx;

    /** Keep portable flag. */
    private final boolean keepPortable;

    /**
     * Constructor.
     *
     * @param cause Root cause.
     * @param ctx Context.
     * @param keepPortable Keep portable flag.
     */
    public PlatformCachePartialUpdateException(CachePartialUpdateException cause, PlatformContext ctx,
        boolean keepPortable) {
        super(cause);

        this.ctx = ctx;
        this.keepPortable = keepPortable;
    }

    /** {@inheritDoc} */
    @Override public PlatformContext context() {
        return ctx;
    }

    /** {@inheritDoc} */
    @Override public void writeData(PortableRawWriterEx writer) {
        Collection keys = ((CachePartialUpdateException)getCause()).failedKeys();

        writer.writeBoolean(keepPortable);

        PlatformUtils.writeNullableCollection(writer, keys);
    }
}
