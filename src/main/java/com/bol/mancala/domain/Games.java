package com.bol.mancala.domain;

import java.util.List;

public interface Games {
    void store(Game game);
    Game loadById(String id);
    List<Game> ongoing();
}
