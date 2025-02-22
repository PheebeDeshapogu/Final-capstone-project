package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGameDao implements GameDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcGameDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Game> getAllGames(){
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM games ORDER BY game_name ASC;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while(rowSet.next()){
            games.add(mapToRowSet(rowSet));
        }

        return games;
    }

    public Game getGameById(int gameId) {
        Game game = null;
        String sql = "SELECT game_name , category_id FROM games  WHERE game_id=?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId);
            if (results.next()) {
                game = mapToRowSet(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return game;
    }

    public Game getGameByName(String gameName) {
        Game game = null;
        String sql = "SELECT game_id, category_id FROM games  WHERE game_id=?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameName);
            if (results.next()) {
                game = mapToRowSet(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return game;
    }


    public Game mapToRowSet(SqlRowSet rowSet) {
        Game game = new Game();
        game.setGameId(rowSet.getInt("game_id"));
        game.setGameName(rowSet.getString("game_name"));
        game.setCategoryId(rowSet.getInt("category_id"));
        return game;
    }
}
