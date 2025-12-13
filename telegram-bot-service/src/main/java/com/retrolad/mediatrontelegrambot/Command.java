package com.retrolad.mediatrontelegrambot;

public enum Command {
    SEARCH,
    MOVIE,
    START,
    WATCHLIST,
    ADD_TO_WATCHLIST,
    REMOVE_FROM_WATCHLIST;

    public static Command from(String value) {
        value = value.replaceAll("-", "_");
        return Command.valueOf(value.toUpperCase());
    }
}
