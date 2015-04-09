/**
* AppEducacionalEvent.java
* appEducacionalVaadin
* 26/12/2014 21:00:42
* Copyright David
* com.app.presenter.event
*/
package com.app.presenter.event;

import java.util.Collection;

import javax.transaction.Transaction;

import org.springframework.security.core.context.SecurityContextHolder;

import com.app.ui.user.menu.UserMenuHelper;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
/**
 * @author David
 *
 */
public abstract class EventComunication {

	
	
	public static final class UserLoginRequestedEvent {
        private final String userName, password;

        public UserLoginRequestedEvent(final String userName,
                final String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {
    	
    	
    	public UserLoggedOutEvent(){
    		SecurityContextHolder.clearContext();
    		Page.getCurrent().reload();
    	}
    	
    }

    public static class NotificationsCountUpdatedEvent {
    }

    public static final class ReportsCountUpdatedEvent {
        private final int count;

        public ReportsCountUpdatedEvent(final int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

    }

    public static final class TransactionReportEvent {
        private final Collection<Transaction> transactions;

        public TransactionReportEvent(final Collection<Transaction> transactions) {
            this.transactions = transactions;
        }

        public Collection<Transaction> getTransactions() {
            return transactions;
        }
    }

    public static final class PostViewChangeEvent {
        private final UserMenuHelper view;

        public PostViewChangeEvent(final UserMenuHelper view) {
            this.view = view;
        }

        public UserMenuHelper getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent {
    }

    public static class ProfileUpdatedEvent {
    	
    	public ProfileUpdatedEvent(){
    		Page.getCurrent().reload();
    	}
    	
    }

	
}
