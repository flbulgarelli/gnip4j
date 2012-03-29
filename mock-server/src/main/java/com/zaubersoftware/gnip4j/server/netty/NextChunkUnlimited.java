/**
 * Copyright (c) 2011-2012 Zauber S.A. <http://www.zaubersoftware.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zaubersoftware.gnip4j.server.netty;

import static org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer;

import java.util.Collection;

import org.jboss.netty.handler.codec.http.DefaultHttpChunk;


public class NextChunkUnlimited extends NextChunkStrategy{

    /**
     * 
     * Creates the NextChunkIterator.
     *
     * @param channel
     * @param iterator
     */
    public NextChunkUnlimited(final Collection<String> activities) {
        super(activities);
    }

    @Override
    public Object nextChunk() throws Exception {
        if (getIterator().hasNext() == false) {
            setIterator(getActivities().iterator());  
        }
        
        final String activity = getIterator().next();
        final DefaultHttpChunk chunk = new DefaultHttpChunk(wrappedBuffer(activity.getBytes("UTF-8")));
        return chunk;
    }

}
