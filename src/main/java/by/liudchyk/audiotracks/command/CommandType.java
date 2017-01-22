package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.command.admin.*;
import by.liudchyk.audiotracks.command.account.*;
import by.liudchyk.audiotracks.command.client.*;

/**
 * Class {@code CommandType} is used as enum to create an
 * object of necessary command
 *
 * @author Liudchyk Valeriya
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
            this.command = new ChangeCommand();
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
    SWITCH_PAGE {
        {
            this.command = new SwitchPageCommand();
        }
    },
    GENRE_ORDER {
        {
            this.command = new GenreOrderCommand();
        }
    },
    SEARCH {
        {
            this.command = new SearchOrderCommand();
        }
    },
    TRACK_INFO {
        {
            this.command = new TrackInfoCommand();
        }
    },
    COMMENT_ADD {
        {
            this.command = new CommentAddCommand();
        }
    },
    COMMENT_DELETE {
        {
            this.command = new CommentDeleteCommand();
        }
    },
    ORDERS {
        {
            this.command = new OrdersCommand();
        }
    },
    DOWNLOAD {
        {
            this.command = new DownloadCommand();
        }
    },
    DELETE_TRACK {
        {
            this.command = new DeleteTrackCommand();
        }
    },
    DELETED_TRACKS {
        {
            this.command = new DeletedTracksCommand();
        }
    },
    RECOVER_TRACK {
        {
            this.command = new RecoverTrackCommand();
        }
    },
    PRICE_CHANGE {
        {
            this.command = new ChangePriceCommand();
        }
    },
    ADD_TRACK {
        {
            this.command = new AddTrackCommand();
        }
    },
    USER_INFO {
        {
            this.command = new UserInfoCommand();
        }
    },
    SET_BONUS {
        {
            this.command = new SetBonusCommand();
        }
    },
    ORDER_TRACK {
        {
            this.command = new OrderTrackCommand();
        }
    },
    BUY_TRACK {
        {
            this.command = new BuyTrackCommand();
        }
    },
    ALL_USERS {
        {
            this.command = new AllUsersCommand();
        }
    },
    LENGTH_CHANGE {
        {
            this.command = new LengthChangeCommand();
        }
    },
    TITLE_CHANGE {
        {
            this.command = new TitleChangeCommand();
        }
    },
    ARTIST_CHANGE {
        {
            this.command = new ArtistChangeCommand();
        }
    },
    GENRE_CHANGE {
        {
            this.command = new GenreChangeCommand();
        }
    };
    ActionCommand command;

    /**
     * Gets current command object
     *
     * @return ActionCommand object
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }
}