package by.liudchyk.audiotracks.command;

/**
 * Created by Admin on 23.12.2016.
 */

public enum CommandType {
    LANG {
        {
            this.command = new LanguageCommand();
        }
    },
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },
    ACCOUNT {
        {
            this.command = new AccountCommand();
        }
    },
    LOGIN_CHANGE {
        {
            this.command = new ChangeLoginCommand();
        }
    },
    EMAIL_CHANGE {
        {
            this.command = new ChangeEmailCommand();
        }
    },
    CARD_CHANGE {
        {
            this.command = new ChangeCardCommand();
        }
    },
    PASSWORD_CHANGE {
        {
            this.command = new ChangePasswordCommand();
        }
    },
    MONEY_CHANGE {
        {
            this.command = new ChangeMoneyCommand();
        }
    },
    INDEX {
        {
            this.command = new IndexCommand();
        }
    },
    MAIN {
        {
            this.command = new MainCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}