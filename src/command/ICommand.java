package command;

import model.ICrosser;

public interface ICommand {
    void exceuteMove(ICrosser crossers, boolean isBoatOnLeftBank);
}
