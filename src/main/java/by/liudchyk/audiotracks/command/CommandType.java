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
    LOGOUT{
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTRATION{
        {
            this.command = new RegistrationCommand();
        }
    },
    ACCOUNT{
        {
            this.command = new AccountCommand();
        }
    },
    LOGIN_CHANGE{
        {
            this.command = new ChangeLoginCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}