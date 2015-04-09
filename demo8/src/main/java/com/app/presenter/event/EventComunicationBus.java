/**
* AppEducacionalEventBus.java
* appEducacionalVaadin
* 26/12/2014 21:06:33
* Copyright David
* com.app.presenter.event
*/
package com.app.presenter.event;

import com.app.ui.AppUI;
import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionContext;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionHandler;

/**
 * @author David
 * A simple wrapper for Guava event bus. Defines static convenience methods for
 * relevant actions.
 */
public class EventComunicationBus implements SubscriberExceptionHandler{

	private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        AppUI.getEventComunicationBus().eventBus.post(event);
    }

    public static void register(final Object object) {
    	AppUI.getEventComunicationBus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
    	AppUI.getEventComunicationBus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,
            final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }

}
