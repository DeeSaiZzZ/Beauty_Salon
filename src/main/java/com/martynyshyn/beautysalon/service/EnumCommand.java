package com.martynyshyn.beautysalon.service;

import com.martynyshyn.beautysalon.controller.command.common.ChangeLanguageCommand;
import com.martynyshyn.beautysalon.controller.command.common.OpenMasterListCommand;
import com.martynyshyn.beautysalon.controller.command.common.OpenServicesListCommand;
import com.martynyshyn.beautysalon.controller.command.admin.*;
import com.martynyshyn.beautysalon.controller.command.client.*;
import com.martynyshyn.beautysalon.controller.command.log.reg.LogOutCommand;
import com.martynyshyn.beautysalon.controller.command.log.reg.LoggingUserCommand;
import com.martynyshyn.beautysalon.controller.command.log.reg.RegisteredUserCommand;
import com.martynyshyn.beautysalon.controller.command.master.CompleteOrderCommand;
import com.martynyshyn.beautysalon.controller.command.master.ShowTimetableCommand;

/**
 * Enum whit command const.
 *
 * @author N.Martynyshyn
 */

public enum EnumCommand {
    //======================================= ADMIN COMMAND ======================================================//

    ACCEPT_REQUEST(new AcceptRequestCommand()),

    CANCEL_REQUEST(new CancelRequestCommand()),

    CHECK_REQUEST(new CheckRequestCommand()),

    CHECK_ALL_ORDER(new CheckAllOrderCommand()),

    MODERATE_ORDER(new ModerateOrderCommand()),

    //======================================= MASTER COMMAND ======================================================//

    SHOW_TIMETABLE(new ShowTimetableCommand()),

    COMPLETE_ORDER(new CompleteOrderCommand()),

    //======================================= CLIENT COMMAND ======================================================//

    OPEN_ORDER_REG_FORM(new OpenOrderFormCommand()),

    OPEN_MY_ORDER_LIST(new OpenMyOrderListCommand()),

    FIND_SERVICE(new FindServiceCommand()),

    CREATE_REQUEST(new CreateRequestCommand()),

    FIND_TIMESLOTS(new FindTimeSlotsCommand()),

    //======================================= COMMON COMMAND ======================================================//

    OPEN_MASTER_LIST(new OpenMasterListCommand()),

    OPEN_SERVICE_LIST(new OpenServicesListCommand()),

    CHANGE_LAN(new ChangeLanguageCommand()),

    //======================================= LOG/REG COMMAND ======================================================//

    LOGGING_USER(new LoggingUserCommand()),

    LOGOUT_USER(new LogOutCommand()),

    REGISTERED_USER(new RegisteredUserCommand());

    EnumCommand(Command command) {
        this.command = command;
    }

    final Command command;

    public Command getCommand() {
        return command;
    }
}
