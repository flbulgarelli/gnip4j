package com.zaubersoftware.gnip4j.http;

import net.sf.staccatocommons.control.monad.Monad;
import net.sf.staccatocommons.control.monad.Monads;
import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.lang.tuple.Tuples;

import com.zaubersoftware.gnip4j.api.GnipFacade;
import com.zaubersoftware.gnip4j.api.GnipStream;
import com.zaubersoftware.gnip4j.api.model.Activity;

public class GnipMonads {

    public static Monad<Tuple2<Activity, GnipStream>> from(final GnipFacade gnip, String account,
            String streamName) {
        return Monads.from(new GnipStreamMonadicValue(gnip, account, streamName));
    }

    public static Monad<Activity> fromActivity(final GnipFacade gnip, String account, String streamName) {
        return Monads.from(new GnipStreamMonadicValue(gnip, account, streamName)).map(
                Tuples.<Activity> first());
    }
}