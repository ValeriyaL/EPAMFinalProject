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
    },
    TRACKS_ORDER {
        {
            this.command = new TracksInOrderCommand();
        }
    },
    SWITCH_PAGE{
        {
            this.command = new SwitchPageCommand();
        }
    },
    GENRE_ORDER{
        {
            this.command = new GenreOrderCommand();
        }
    },
    SEARCH{
        {
            this.command = new SearchOrderCommand();
        }
    },
    TRACK_INFO{
        {
            this.command = new TrackInfoCommand();
        }
    },
    COMMENT_ADD{
        {
            this.command = new CommentAddCommand();
        }
    },
    COMMENT_DELETE{
        {
            this.command = new CommentDeleteCommand();
        }
    },
    ORDERS{
        {
            this.command = new OrdersCommand();
        }
    },
    DOWNLOAD{
        {
            this.command = new DownloadCommand();
        }
    },
    DELETE_TRACK{
        {
            this.command = new DeleteTrackCommand();
        }
    },
    DELETED_TRACKS{
        {
            this.command = new DeletedTracksCommand();
        }
    },
    RECOVER_TRACK{
        {
            this.command = new RecoverTrackCommand();
        }
    },
    PRICE_CHANGE{
        {
            this.command = new ChangePriceCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}