package com.zaubersoftware.gnip4j.http;

import static net.sf.staccatocommons.lang.tuple.Tuples.*;
import net.sf.staccatocommons.control.monad.Monad;
import net.sf.staccatocommons.control.monad.MonadicValue;
import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.lang.SoftException;

import com.sun.media.sound.SoftEnvelopeGenerator;
import com.zaubersoftware.gnip4j.api.GnipFacade;
import com.zaubersoftware.gnip4j.api.GnipStream;
import com.zaubersoftware.gnip4j.api.StreamNotificationAdapter;
import com.zaubersoftware.gnip4j.api.model.Activity;

public class GnipStreamMonadicValue implements MonadicValue<Tuple2<Activity, GnipStream>> {

    final GnipFacade gnip;
    String account;
    String streamName;

    public GnipStreamMonadicValue(GnipFacade gnip, String account, String streamName) {
        this.gnip = gnip;
        this.account = account;
        this.streamName = streamName;
    }

    @Override
    public <T> void eval(final Applicable<? super Tuple2<Activity, GnipStream>, Monad<T>> arg0) {
        try {
            gnip.createStream(account, streamName, new StreamNotificationAdapter() {
                public void notify(Activity activity, GnipStream stream) {
                    arg0.apply(_(activity, stream)).run();
                }
            }).await();
        } catch (InterruptedException e) {
            throw SoftException.soften(e);
        }

    }

}
